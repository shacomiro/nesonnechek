package com.shacomiro.makeabook.domain.rds.ebookfile.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFileExtension;
import com.shacomiro.makeabook.domain.rds.ebookfile.repository.EbookFileRepository;
import com.shacomiro.makeabook.ebook.EbookManager;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.error.FileIOException;

@Service
public class EbookFileService {
	private static final String RESOURCES_DIR = "./files";
	private final EbookManager ebookManager;
	private final EbookFileRepository ebookFileRepository;
	@Value("${makeabook-app-api.download-url-prefix}")
	private String downloadUrlPrefix;

	public EbookFileService(EbookFileRepository ebookFileRepository) {
		this.ebookFileRepository = ebookFileRepository;
		this.ebookManager = new EbookManager(RESOURCES_DIR);
	}

	public EbookFile createEpub(ByteArrayResource resource, String uuid, EbookFileExtension ebookFileExtension,
			String fileName) {
		EpubFileInfo epubFileInfo = null;
		EbookFile ebookFile = null;
		String ebookExtension = null;

		if (ebookFileExtension.equals(EbookFileExtension.EPUB2)) {
			epubFileInfo = ebookManager.translateTxtToEpub2(resource.getByteArray(), fileName);
			ebookExtension = EbookFileExtension.EPUB2.getEbookExt().toLowerCase();

			ebookFile = ebookFileRepository.save(EbookFile.builder()
					.uuid(uuid)
					.filename(epubFileInfo.getFileName())
					.fileType(ebookExtension)
					.downloadUrl(/*"http://localhost:8080/api/ebook/epub2/"*/
							downloadUrlPrefix + "/" + ebookExtension + "/download/" + epubFileInfo.getFileName()
									+ ".epub")
					.validDurationDay(1)
					.build());
		}

		return ebookFile;
	}

	public Optional<ByteArrayResource> getEpubAsResource(EbookFileExtension ebookFileExtension, String fileName) {
		Path path = ebookManager.getEpubFilePath(ebookFileExtension.toString(), fileName);
		ByteArrayResource resource = null;

		if (Files.exists(path)) {
			try {
				resource = new ByteArrayResource(Files.readAllBytes(path));
			} catch (IOException e) {
				throw new FileIOException("Fail to load file", e);
			}
		}

		return Optional.ofNullable(resource);
	}
}
