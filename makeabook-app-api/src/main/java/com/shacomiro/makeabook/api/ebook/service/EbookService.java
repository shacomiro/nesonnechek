package com.shacomiro.makeabook.api.ebook.service;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

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
}
