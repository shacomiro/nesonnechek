package com.shacomiro.makeabook.api.ebook.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shacomiro.makeabook.api.ebook.service.EbookService;
import com.shacomiro.makeabook.api.error.NotExistException;
import com.shacomiro.makeabook.api.error.NotFoundException;
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
	public ResponseEntity<?> uploadTextFile(MultipartFile file) {
		try {
			if (file == null) {
				throw new NotExistException("no file uploaded");
			}

			ByteArrayResource resource = new ByteArrayResource(file.getBytes());
			EpubFileInfo info = ebookService.createEpub2(resource, file.getOriginalFilename());

			return ResponseEntity.ok()
					.body(info);
		} catch (EmptyFileException e) {
			return ResponseEntity.ok()
					.body("빈 파일입니다.");
		} catch (NotExistException e) {
			return ResponseEntity.ok()
					.body("파일이 업로드되지 않았습니다.");
		} catch (IOException e) {
			return ResponseEntity.ok()
					.body("파일 변환에 실패했습니다.");
		}
	}

	@GetMapping(path = "download/{filename}")
	public ResponseEntity<?> downloadEbookFile(@PathVariable String filename) {
		log.info("RootPath = " + System.getProperty("user.dir"));
		try {
			ByteArrayResource resource = ebookService.getEpubAsResource(filename);

			return ResponseEntity
					.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment")
							.filename(filename, StandardCharsets.UTF_8)
							.build()
							.toString())
					.header(HttpHeaders.CONTENT_TYPE, "application/epub+zip")
					.header(HttpHeaders.CONTENT_LENGTH, Long.toString(resource.getByteArray().length))
					.body(resource);
		} catch (NotFoundException e) {
			return ResponseEntity.ok()
					.body(e.getMessage());
		}
	}
}
