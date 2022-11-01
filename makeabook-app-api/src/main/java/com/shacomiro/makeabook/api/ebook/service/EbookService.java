package com.shacomiro.makeabook.api.ebook.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.api.ebook.domain.EpubVersion;
import com.shacomiro.makeabook.api.global.error.NotFoundException;
import com.shacomiro.makeabook.ebook.EbookManager;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.error.FileIOException;

@Service
public class EbookService {
	private static final String RESOURCES_DIR = "./makeabook-app-api/src/main/resources";
	private final EbookManager ebookManager;

	public EbookService() {
		this.ebookManager = new EbookManager(RESOURCES_DIR);
	}

	public EpubFileInfo createEpub(ByteArrayResource resource, EpubVersion epubVersion, String fileName) {
		EpubFileInfo epubFileInfo = null;

		if (epubVersion.equals(EpubVersion.EPUB2)) {
			epubFileInfo = ebookManager.translateTxtToEpub2(resource.getByteArray(), fileName);
		}

		return epubFileInfo;
	}

	public ByteArrayResource getEpubAsResource(EpubVersion epubVersion, String fileName) {
		Path path = ebookManager.getEpubFilePath(epubVersion.toString(), fileName);

		if (Files.exists(path)) {
			try {
				return new ByteArrayResource(Files.readAllBytes(path));
			} catch (IOException e) {
				throw new FileIOException("fail to load file", e);
			}
		} else {
			throw new NotFoundException(fileName + " does not exist");
		}
	}
}
