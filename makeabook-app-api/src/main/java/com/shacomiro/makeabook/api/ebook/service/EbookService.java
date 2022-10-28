package com.shacomiro.makeabook.api.ebook.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.api.error.NotFoundException;
import com.shacomiro.makeabook.ebook.EbookManager;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;

@Service
public class EbookService {
	private EbookManager ebookManager;

	public EbookService() {
		this.ebookManager = new EbookManager();
	}

	public EpubFileInfo createEpub2(ByteArrayResource resource, String fileName) {
		return ebookManager.translateTxtToEpub2(resource.getByteArray(), fileName);
	}

	public ByteArrayResource getEpubAsResource(String fileName) {
		Path path = ebookManager.getExistEpubFilePath(fileName);

		if (Files.exists(path)) {
			try {
				return new ByteArrayResource(Files.readAllBytes(path));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new NotFoundException(fileName + " does not exist");
		}
	}
}
