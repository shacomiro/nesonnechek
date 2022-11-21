package com.shacomiro.makeabook.api.ebook.api;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

import com.shacomiro.makeabook.api.global.error.ExpiredException;
import com.shacomiro.makeabook.api.global.error.NotFoundException;
import com.shacomiro.makeabook.api.global.hateoas.assembler.EbookFileResponseModelAssembler;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFileExtension;
import com.shacomiro.makeabook.domain.rds.ebookfile.service.EbookFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "api/ebook")
public class EbookRestApi {
	private final EbookFileService ebookFileService;
	private final EbookFileResponseModelAssembler ebookFileResponseModelAssembler;

	public EbookRestApi(EbookFileService ebookFileService, EbookFileResponseModelAssembler ebookFileResponseModelAssembler) {
		this.ebookFileService = ebookFileService;
		this.ebookFileResponseModelAssembler = ebookFileResponseModelAssembler;
	}

	@PostMapping(path = "")
	public ResponseEntity<?> createEbookFile(
			@RequestParam(name = "type", defaultValue = "epub2") EbookFileExtension ebookFileExtension,
			@RequestBody @RequestParam(name = "file") MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalStateException("File is empty");
		} else if (file.getContentType() == null || !file.getContentType().equals(MediaType.TEXT_PLAIN_VALUE)) {
			throw new IllegalArgumentException("File content type is invalid");
		}

		return success(
				ebookFileService.createEpub(file, ebookFileExtension)
						.orElseThrow(() -> new NullPointerException("Fail to create ebook")),
				ebookFileResponseModelAssembler,
				HttpStatus.CREATED
		);
	}

	@GetMapping(path = "{uuid}")
	public ResponseEntity<?> getEbookFile(@PathVariable String uuid) {
		return success(
				ebookFileService.findEbookFileByUuid(uuid)
						.orElseThrow(() -> new NotFoundException("File is not found")),
				ebookFileResponseModelAssembler,
				HttpStatus.OK
		);
	}

	@GetMapping(path = "{uuid}/file", produces = "application/epub+zip")
	public ResponseEntity<Resource> downloadEbookFile(@PathVariable String uuid) {
		return ebookFileService.findEbookFileByUuid(uuid)
				.map(ebookFile -> {
					if (ebookFile.isExpired()) {
						throw new ExpiredException("File is expired");
					} else {
						return ebookFileService.getEpubAsResource(ebookFile)
								.map(resource -> {
									HttpHeaders headers = new HttpHeaders();
									headers.add(HttpHeaders.CONTENT_DISPOSITION,
											ContentDisposition.builder("attachment")
													.filename(ebookFile.getFilename() + "."
															+ ebookFile.getFileExtension(), StandardCharsets.UTF_8)
													.build()
													.toString());
									headers.add(HttpHeaders.CONTENT_TYPE, "application/epub+zip");
									headers.add(HttpHeaders.CONTENT_LENGTH,
											Long.toString(resource.getByteArray().length));

									return new ResponseEntity<>((Resource)resource, headers, HttpStatus.OK);
								})
								.orElseThrow(() -> new NotFoundException("Fail to load resource"));
					}
				})
				.orElseThrow(() -> new NotFoundException("File does not exist"));
	}
}
