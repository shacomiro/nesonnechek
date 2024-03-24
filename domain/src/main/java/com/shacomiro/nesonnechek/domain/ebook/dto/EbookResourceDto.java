package com.shacomiro.nesonnechek.domain.ebook.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.core.io.ByteArrayResource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EbookResourceDto {
	@NotNull(message = "Ebook resource must be provided")
	private ByteArrayResource ebookResource;
	@NotBlank(message = "Ebook filename must be provided")
	private String ebookFilename;
}
