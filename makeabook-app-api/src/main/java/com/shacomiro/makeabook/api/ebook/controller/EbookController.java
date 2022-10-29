package com.shacomiro.makeabook.api.ebook.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shacomiro.makeabook.api.ebook.service.EbookService;
import com.shacomiro.makeabook.api.error.NotExistException;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.error.EmptyFileException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "api/ebook")
public class EbookController {
	private final EbookService ebookService;

	public EbookController(EbookService ebookService) {
		this.ebookService = ebookService;
	}

	@PostMapping(path = "upload")
	public EpubFileInfo uploadTextFile(MultipartFile file) {
		if (file == null) {
			throw new NotExistException("No file uploaded");
		} else if (file.isEmpty()) {
			throw new EmptyFileException("Empty file is uploaded");
		}

		try {
			return ebookService.createEpub2(new ByteArrayResource(file.getBytes()), file.getOriginalFilename());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping(path = "download/{filename}")
	public ResponseEntity<Resource> downloadEbookFile(@PathVariable String filename) {
		ByteArrayResource resource = ebookService.getEpubAsResource(filename);

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
