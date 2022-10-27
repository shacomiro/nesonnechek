package com.shacomiro.makeabook.api.ebook.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.shacomiro.makeabook.api.ebook.error.NotFoundException;
import com.shacomiro.makeabook.ebook.EbookManager;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;

@Service
public class EbookService {
	private EbookManager ebookManager;

	public EbookService() {
		this.ebookManager = new EbookManager();
	}

	public EpubFileInfo createEpub2(ByteArrayResource resource, String fileName) throws IOException {
		return ebookManager.translateTxtToEpub2(resource.getInputStream(), fileName);
	}

	public ByteArrayResource getEpubAsResource(String fileName) throws IOException {
		Path path = ebookManager.getExistEpubFilePath(fileName);
		File targetFile = new File(path.toString());

		if (targetFile.exists()) {
			return new ByteArrayResource(Files.readAllBytes(path));
		} else {
			throw new NotFoundException(fileName + " does not exist");
		}
	}
}
