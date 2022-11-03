package com.shacomiro.makeabook.api.ebook.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

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

import com.shacomiro.makeabook.api.ebook.dto.EbookResultResponse;
import com.shacomiro.makeabook.api.global.error.NotFoundException;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFileExtension;
import com.shacomiro.makeabook.domain.rds.ebookfile.service.EbookFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "api/ebook")
public class EbookApi {
	private final EbookFileService ebookFileService;

	public EbookApi(EbookFileService ebookFileService) {
		this.ebookFileService = ebookFileService;
	}

	@PostMapping(path = "{ebookFileExtension}/upload")
	public EbookResultResponse uploadTextFile(@PathVariable EbookFileExtension ebookFileExtension,
			@RequestBody @RequestParam(name = "file") MultipartFile file) {
		String uuid = UUID.randomUUID().toString();

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

		return Optional.ofNullable(
						ebookFileService.createEpub(resource, uuid, ebookFileExtension, file.getOriginalFilename()))
				.map(EbookResultResponse::new)
				.orElseThrow(() -> new NullPointerException("Fail to create ebook"));
	}

	@GetMapping(path = "{ebookFileExtension}/download/{filename}")
	public ResponseEntity<Resource> downloadEbookFile(@PathVariable EbookFileExtension ebookFileExtension,
			@PathVariable String filename) {
		return ebookFileService.getEpubAsResource(ebookFileExtension, filename)
				.map(resource -> {
					HttpHeaders headers = new HttpHeaders();
					headers.add(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment")
							.filename(filename, StandardCharsets.UTF_8)
							.build()
							.toString());
					headers.add(HttpHeaders.CONTENT_TYPE, "application/epub+zip");
					headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(resource.getByteArray().length));

					return new ResponseEntity<>((Resource)resource, headers, HttpStatus.OK);
				})
				.orElseThrow(() -> new NotFoundException("file does not exist"));
	}
}
