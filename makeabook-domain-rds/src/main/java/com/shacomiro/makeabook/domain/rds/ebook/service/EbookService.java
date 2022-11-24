package com.shacomiro.makeabook.domain.rds.ebook.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shacomiro.makeabook.core.util.IOUtils;
import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookExtension;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.ebook.repository.EbookRepository;
import com.shacomiro.makeabook.ebook.EbookManager;
import com.shacomiro.makeabook.ebook.domain.ContentTempFileInfo;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.error.FileIOException;

@Service
@Transactional
public class EbookService {
	private static final String RESOURCES_DIR = "./files";
	private final EbookManager ebookManager;
	private final EbookRepository ebookRepository;

	public EbookService(EbookRepository ebookRepository) {
		this.ebookRepository = ebookRepository;
		this.ebookManager = new EbookManager(RESOURCES_DIR);
	}

	public Optional<Ebook> createEbook(MultipartFile file, EbookType ebookType) {
		Optional<Ebook> ebook = Optional.empty();
		String uuid = UUID.randomUUID().toString();
		EpubFileInfo epubFileInfo;

		ContentTempFileInfo contentTempFileInfo = saveUploadToTempFile(file);

		if (ebookType.equals(EbookType.EPUB2)) {
			epubFileInfo = ebookManager.translateTxtToEpub2(uuid, file.getOriginalFilename(), contentTempFileInfo);

			ebook = Optional.of(ebookRepository
					.save(Ebook.byEbookFileInfo()
							.uuid(uuid)
							.name(epubFileInfo.getFileName())
							.type(EbookType.EPUB2.getType())
							.extension(EbookExtension.EPUB.getExtension())
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

	public Optional<Ebook> findEbookByUuid(String uuid) {
		return ebookRepository.findByUuid(uuid);
	}

	public Optional<ByteArrayResource> getEpubAsResource(Ebook ebook) {
		Path path = ebookManager.getEpubFilePath(ebook.getType(), ebook.getFileName());

		if (Files.exists(path)) {
			try {
				ebook.addDownloadCount();
				ebookRepository.save(ebook);

				return Optional.of(new ByteArrayResource(Files.readAllBytes(path), ebook.getName()));
			} catch (IOException e) {
				throw new FileIOException("Fail to load file", e);
			}
		} else {
			return Optional.empty();
		}
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
