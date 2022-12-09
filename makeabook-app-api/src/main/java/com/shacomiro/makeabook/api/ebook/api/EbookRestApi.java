package com.shacomiro.makeabook.api.ebook.api;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.nio.charset.StandardCharsets;

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

import com.shacomiro.makeabook.api.global.error.NotFoundException;
import com.shacomiro.makeabook.api.global.hateoas.assembler.EbookResponseModelAssembler;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.ebook.service.EbookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "api/ebook")
public class EbookRestApi {
	private final EbookService ebookService;
	private final EbookResponseModelAssembler ebookResponseModelAssembler;

	public EbookRestApi(EbookService ebookService, EbookResponseModelAssembler ebookResponseModelAssembler) {
		this.ebookService = ebookService;
		this.ebookResponseModelAssembler = ebookResponseModelAssembler;
	}

	@PostMapping(path = "")
	public ResponseEntity<?> createEbook(
			@RequestParam(name = "type", defaultValue = "epub2") EbookType ebookType,
			@RequestBody @RequestParam(name = "file") MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalStateException("Upload file is empty");
		} else if (file.getContentType() == null || !file.getContentType().equals(MediaType.TEXT_PLAIN_VALUE)) {
			throw new IllegalArgumentException("Invalid Content type for upload file");
		}

		return success(
				ebookService.createEbook(file, ebookType)
						.orElseThrow(() -> new NullPointerException("Fail to create ebook")),
				ebookResponseModelAssembler,
				HttpStatus.CREATED
		);
	}

	@GetMapping(path = "{uuid}")
	public ResponseEntity<?> getEbook(@PathVariable String uuid) {
		return success(
				ebookService.findEbookByUuid(uuid)
						.orElseThrow(() -> new NotFoundException("Ebook is not found")),
				ebookResponseModelAssembler,
				HttpStatus.OK
		);
	}

	@GetMapping(path = "{uuid}/file", produces = "application/epub+zip")
	public ResponseEntity<Resource> downloadEbook(@PathVariable String uuid) {
		return ebookService.findEbookByUuid(uuid)
				.map(ebook -> ebookService.getEbookResource(ebook)
						.map(resource -> {
							HttpHeaders headers = new HttpHeaders();
							headers.add(HttpHeaders.CONTENT_DISPOSITION,
									ContentDisposition.builder("attachment")
											.filename(ebook.getEbookFileName(), StandardCharsets.UTF_8)
											.build()
											.toString());
							headers.add(HttpHeaders.CONTENT_TYPE, "application/epub+zip");
							headers.add(HttpHeaders.CONTENT_LENGTH,
									Long.toString(resource.getByteArray().length));

							return new ResponseEntity<>((Resource)resource, headers, HttpStatus.OK);
						})
						.orElseThrow(() -> new NotFoundException("Fail to load ebook resource")))
				.orElseThrow(() -> new NotFoundException("Ebook does not exist"));
	}
}
