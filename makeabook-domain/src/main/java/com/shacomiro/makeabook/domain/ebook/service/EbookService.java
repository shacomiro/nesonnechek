package com.shacomiro.makeabook.domain.ebook.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shacomiro.epub.EbookManager;
import com.shacomiro.epub.domain.ContentTempFileInfo;
import com.shacomiro.epub.domain.EpubFileInfo;
import com.shacomiro.makeabook.core.global.exception.FileIOException;
import com.shacomiro.makeabook.core.util.IOUtils;
import com.shacomiro.makeabook.domain.ebook.dto.EbookRequestDto;
import com.shacomiro.makeabook.domain.ebook.dto.EbookResourceDto;
import com.shacomiro.makeabook.domain.ebook.dto.FileDto;
import com.shacomiro.makeabook.domain.ebook.exception.EbookNotFoundException;
import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.ebook.service.EbookRdsService;
import com.shacomiro.makeabook.domain.rds.user.entity.Email;
import com.shacomiro.makeabook.domain.rds.user.entity.User;
import com.shacomiro.makeabook.domain.rds.user.service.UserRdsService;
import com.shacomiro.makeabook.domain.user.exception.UserNotFoundException;

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

	public Optional<Ebook> createEbook(EbookRequestDto ebookRequestDto) {
		Optional<Ebook> ebook = Optional.empty();
		EpubFileInfo epubFileInfo;
		List<ContentTempFileInfo> contentTempFileInfos = saveUploadToTempFile(ebookRequestDto.getFiles());

		if (ebookRequestDto.getEbookType().equals(EbookType.EPUB2)) {
			ContentTempFileInfo txtTempFileInfo = contentTempFileInfos.get(0);
			epubFileInfo = ebookManager.translateTxtToEpub2(txtTempFileInfo.getUuid(), txtTempFileInfo.getOriginalTempFilename(),
					txtTempFileInfo);

			ebook = Optional.of(ebookRdsService
					.save(Ebook.byEbookCreationResult()
							.uuid(txtTempFileInfo.getUuid())
							.name(epubFileInfo.getFileName())
							.type(EbookType.EPUB2)
							.user(userRdsService.findByEmail(Email.byValue().value(ebookRequestDto.getUploader()).build())
									.orElseThrow(() ->
											new UserNotFoundException(
													"Could not find user '" + ebookRequestDto.getUploader() + "'."))
							)
							.build())
			);
		}

		for (ContentTempFileInfo tempFile : contentTempFileInfos) {
			try {
				Files.deleteIfExists(tempFile.getTempFilePath());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
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

	private List<ContentTempFileInfo> saveUploadToTempFile(List<FileDto> files) {
		Path contentsBasePath = Paths.get(ebookManager.getContentsBasePath()).normalize().toAbsolutePath();

		return files.stream()
				.map(
						file -> {
							Path tempUploadFilePath = IOUtils.createTempFile(contentsBasePath, file.getExtension());
							try (OutputStream os = Files.newOutputStream(tempUploadFilePath)) {
								os.write(file.getFileBais().readAllBytes());
							} catch (IOException e) {
								throw new RuntimeException(e);
							}

							return new ContentTempFileInfo(UUID.randomUUID().toString(), tempUploadFilePath,
									file.getOriginalFilename());
						}
				).collect(Collectors.toList());
	}
}
