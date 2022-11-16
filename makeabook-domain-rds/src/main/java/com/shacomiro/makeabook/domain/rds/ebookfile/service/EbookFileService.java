package com.shacomiro.makeabook.domain.rds.ebookfile.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shacomiro.makeabook.core.util.IOUtils;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFileExtension;
import com.shacomiro.makeabook.domain.rds.ebookfile.repository.EbookFileRepository;
import com.shacomiro.makeabook.ebook.EbookManager;
import com.shacomiro.makeabook.ebook.domain.ContentTempFileInfo;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.error.FileIOException;

@Service
@Transactional
public class EbookFileService {
	private static final String RESOURCES_DIR = "./files";
	private final EbookManager ebookManager;
	private final EbookFileRepository ebookFileRepository;
	@Value("${secret-key.api-server-url}")
	private String apiServerUrl;

	public EbookFileService(EbookFileRepository ebookFileRepository) {
		this.ebookFileRepository = ebookFileRepository;
		this.ebookManager = new EbookManager(RESOURCES_DIR);
	}

	public Optional<EbookFile> createEpub(MultipartFile file, EbookFileExtension ebookFileExtension) {
		Optional<EbookFile> ebookFile = Optional.empty();
		String uuid = UUID.randomUUID().toString();
		EpubFileInfo epubFileInfo;
		String ebookExtension;

		ContentTempFileInfo contentTempFileInfo = saveUploadToTempFile(file);

		if (ebookFileExtension.equals(EbookFileExtension.EPUB2)) {
			epubFileInfo = ebookManager.translateTxtToEpub2(uuid, file.getOriginalFilename(), contentTempFileInfo);
			ebookExtension = EbookFileExtension.EPUB2.getEbookExt().toLowerCase();

			ebookFile = Optional.of(ebookFileRepository
					.save(EbookFile.byEbookFileInfo()
							.uuid(uuid)
							.filename(epubFileInfo.getFileName())
							.fileType(ebookExtension)
							.fileExtension("epub")
							.downloadUrl(apiServerUrl + "/api/ebook/" + uuid)
							.build())
			);
		}

		try {
			Files.deleteIfExists(contentTempFileInfo.getTxtTempFilePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return ebookFile;
	}

	public Optional<EbookFile> findEbookFileByUuid(String uuid) {
		return ebookFileRepository.findByUuid(uuid);
	}

	public Optional<ByteArrayResource> getEpubAsResource(EbookFile ebookFile) {
		Path path = ebookManager.getEpubFilePath(ebookFile.getFileType(),
				ebookFile.getUuid() + "." + ebookFile.getFileExtension());

		if (Files.exists(path)) {
			try {
				ebookFile.addDownloadCount();
				ebookFileRepository.save(ebookFile);

				return Optional.of(new ByteArrayResource(Files.readAllBytes(path), ebookFile.getFilename()));
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
