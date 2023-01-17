package com.shacomiro.makeabook.domain.ebook.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shacomiro.makeabook.core.global.exception.FileIOException;
import com.shacomiro.makeabook.core.util.IOUtils;
import com.shacomiro.makeabook.domain.ebook.dto.EbookResourceDto;
import com.shacomiro.makeabook.domain.ebook.exception.EbookNotFoundException;
import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.ebook.service.EbookRdsService;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.service.UserRdsService;
import com.shacomiro.makeabook.domain.user.exception.UserNotFoundException;
import com.shacomiro.makeabook.ebook.EbookManager;
import com.shacomiro.makeabook.ebook.domain.ContentTempFileInfo;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EbookService {
	private static final String RESOURCES_DIR = "./files";
	private final EbookRdsService ebookRdsService;
	private final UserRdsService userRdsService;
	private EbookManager ebookManager;

	@PostConstruct
	public void initialize() {
		this.ebookManager = new EbookManager(RESOURCES_DIR);
	}

	public Optional<Ebook> createEbook(MultipartFile file, EbookType ebookType, String emailValue) {
		Optional<Ebook> ebook = Optional.empty();
		String uuid = UUID.randomUUID().toString();
		EpubFileInfo epubFileInfo;

		ContentTempFileInfo contentTempFileInfo = saveUploadToTempFile(file);

		if (ebookType.equals(EbookType.EPUB2)) {
			epubFileInfo = ebookManager.translateTxtToEpub2(uuid, file.getOriginalFilename(), contentTempFileInfo);

			ebook = Optional.of(ebookRdsService
					.save(Ebook.byEbookCreationResult()
							.uuid(uuid)
							.name(epubFileInfo.getFileName())
							.type(EbookType.EPUB2)
							.user(userRdsService.findByEmail(Email.byValue().value(emailValue).build())
									.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."))
							)
							.build())
			);
		}

		try {
			Files.deleteIfExists(contentTempFileInfo.getTxtTempFilePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return ebook;
	}

	public Page<Ebook> findEbooksByUserId(Pageable pageable, Long userId) {
		return ebookRdsService.findAllByUserId(pageable, userId);
	}

	public Ebook findEbookByUuidAndEmail(String uuid, String emailValue) {
		User currentUser = userRdsService.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));

		return ebookRdsService.findByUuidAndUser(uuid, currentUser)
				.orElseThrow(() -> new EbookNotFoundException(
						"Could not find Ebook for user '" + emailValue + "' with UUID '" + uuid + "'."));
	}

	public EbookResourceDto getEbookResourceByUuidAndEmail(String uuid, String emailValue) {
		User currentUser = userRdsService.findByEmail(Email.byValue().value(emailValue).build())
				.orElseThrow(() -> new UserNotFoundException("Could not find user '" + emailValue + "'."));
		Ebook currentEbook = ebookRdsService.findByUuidAndUser(uuid, currentUser)
				.orElseThrow(() -> new EbookNotFoundException(
						"Could not find Ebook for user '" + emailValue + "' with UUID '" + uuid + "'."));

		currentEbook.verifyExpiration();
		currentEbook.addDownloadCount();
		ebookRdsService.save(currentEbook);

		Path path = ebookManager.getEpubFilePath(currentEbook.getType().getValue(), currentEbook.getOriginalFilename());
		if (Files.notExists(path)) {
			throw new FileIOException("Ebook resource not found.");
		}

		ByteArrayResource ebookResource;
		try {
			ebookResource = new ByteArrayResource(Files.readAllBytes(path));
		} catch (IOException e) {
			throw new FileIOException("Fail to load file", e);
		}

		return new EbookResourceDto(ebookResource, currentEbook.getEbookFilename());
	}

	private ContentTempFileInfo saveUploadToTempFile(MultipartFile txtFile) {
		Path tempUploadFilePath = IOUtils.createTempFile(
				Paths.get(ebookManager.getContentsBasePath()).normalize().toAbsolutePath(),
				"." + FilenameUtils.getExtension(txtFile.getOriginalFilename()));

		try {
			txtFile.transferTo(tempUploadFilePath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return ContentTempFileInfo.builder()
				.txtTempFilePath(tempUploadFilePath)
				.build();
	}
}
