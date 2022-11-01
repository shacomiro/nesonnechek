package com.shacomiro.makeabook.api.ebook.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shacomiro.makeabook.api.ebook.domain.EpubVersion;
import com.shacomiro.makeabook.api.ebook.service.EbookService;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "api/ebook")
public class EbookController {
	private final EbookService ebookService;

	public EbookController(EbookService ebookService) {
		this.ebookService = ebookService;
	}

	@PostMapping(path = "{epubVersion}/upload")
	public EpubFileInfo uploadTextFile(@PathVariable EpubVersion epubVersion,
			@RequestBody @RequestParam(name = "file") MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalStateException("File is empty");
		} else if (file.getContentType() == null || !file.getContentType().equals(MediaType.TEXT_PLAIN_VALUE)) {
			throw new IllegalArgumentException("File content type is invalid");
		}

		ByteArrayResource resource;
		try {
			resource = new ByteArrayResource(file.getBytes());
		} catch (IOException e) {
			throw new IllegalStateException("Fail to read upload file", e);
		}

		return ebookService.createEpub(resource, epubVersion, file.getOriginalFilename());
	}

	@GetMapping(path = "{epubVersion}/download/{filename}")
	public ResponseEntity<Resource> downloadEbookFile(@PathVariable EpubVersion epubVersion,
			@PathVariable String filename) {
		ByteArrayResource resource = ebookService.getEpubAsResource(epubVersion, filename);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment")
				.filename(filename, StandardCharsets.UTF_8)
				.build()
				.toString());
		headers.add(HttpHeaders.CONTENT_TYPE, "application/epub+zip");
		headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(resource.getByteArray().length));

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
}
