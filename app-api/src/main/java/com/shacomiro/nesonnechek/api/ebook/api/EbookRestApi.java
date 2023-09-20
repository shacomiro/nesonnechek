package com.shacomiro.nesonnechek.api.ebook.api;

import static com.shacomiro.nesonnechek.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shacomiro.nesonnechek.api.global.assembers.EbookResponseModelAssembler;
import com.shacomiro.nesonnechek.api.global.security.principal.UserPrincipal;
import com.shacomiro.nesonnechek.core.global.exception.FileIOException;
import com.shacomiro.nesonnechek.domain.ebook.dto.EbookRequestDto;
import com.shacomiro.nesonnechek.domain.ebook.dto.EbookResourceDto;
import com.shacomiro.nesonnechek.domain.ebook.service.EbookService;
import com.shacomiro.nesonnechek.domain.rds.ebook.entity.Ebook;
import com.shacomiro.nesonnechek.domain.rds.ebook.entity.EbookType;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/ebooks")
public class EbookRestApi {
	private final EbookService ebookService;
	private final EbookResponseModelAssembler ebookResponseModelAssembler;

	public EbookRestApi(EbookService ebookService, EbookResponseModelAssembler ebookResponseModelAssembler) {
		this.ebookService = ebookService;
		this.ebookResponseModelAssembler = ebookResponseModelAssembler;
	}

	@PostMapping(path = "txt-ebook")
	public ResponseEntity<?> createTxtEbook(
			@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal,
			@RequestParam(name = "type", defaultValue = "epub2") EbookType ebookType,
			@RequestPart(name = "txtFile") MultipartFile txtFile) {
		if (txtFile.isEmpty()) {
			throw new IllegalStateException("Upload file is empty");
		}
		if (txtFile.getContentType() == null || !txtFile.getContentType().equals(MediaType.TEXT_PLAIN_VALUE)) {
			throw new IllegalArgumentException("Invalid Content type for upload file");
		}

		EbookRequestDto ebookRequestDto;
		try {
			ebookRequestDto = new EbookRequestDto(txtFile.getBytes(), txtFile.getOriginalFilename(), userPrincipal.getEmail(),
					ebookType);
		} catch (IOException e) {
			throw new FileIOException("Fail to read upload file.");
		}

		return success(
				ebookService.createEbook(ebookRequestDto)
						.orElseThrow(() -> new NullPointerException("Fail to create ebook")),
				ebookResponseModelAssembler,
				HttpStatus.CREATED
		);
	}

	@PostMapping(path = "files-ebook")
	public ResponseEntity<?> createFilesEbook() {

		return success(
				(Ebook)null,
				ebookResponseModelAssembler,
				HttpStatus.CREATED
		);
	}

	@GetMapping(path = "{uuid}")
	public ResponseEntity<?> getEbook(@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal, @PathVariable String uuid) {
		return success(
				ebookService.findEbookByUuidAndEmail(uuid, userPrincipal.getEmail()),
				ebookResponseModelAssembler,
				HttpStatus.OK
		);
	}

	@GetMapping(path = "{uuid}/file", produces = "application/epub+zip")
	public ResponseEntity<Resource> downloadEbook(
			@AuthenticationPrincipal @NonNull UserPrincipal userPrincipal, @PathVariable String uuid) {
		EbookResourceDto ebookResourceDto = ebookService.getEbookResourceByUuidAndEmail(uuid, userPrincipal.getEmail());

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION,
				ContentDisposition.builder("attachment")
						.filename(ebookResourceDto.getEbookFilename(), StandardCharsets.UTF_8)
						.build()
						.toString());
		headers.add(HttpHeaders.CONTENT_TYPE, "application/epub+zip");
		headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(ebookResourceDto.getEbookResource().getByteArray().length));

		return new ResponseEntity<>(ebookResourceDto.getEbookResource(), headers, HttpStatus.OK);
	}
}
