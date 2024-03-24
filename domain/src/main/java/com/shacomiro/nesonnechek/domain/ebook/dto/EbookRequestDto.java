package com.shacomiro.nesonnechek.domain.ebook.dto;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;

import com.shacomiro.nesonnechek.domain.rds.ebook.entity.EbookType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EbookRequestDto {
	public final String uuid;
	public final List<FileDto> files;
	public final String uploader;
	public final EbookType ebookType;

	public EbookRequestDto(byte[] txtBytes, String txtOriginalFilename, String uploader, EbookType ebookType) {
		this(UUID.randomUUID().toString(), List.of(new FileDto(new ByteArrayInputStream(txtBytes), txtOriginalFilename,
				"." + FilenameUtils.getExtension(txtOriginalFilename))), uploader, ebookType);
	}
}
