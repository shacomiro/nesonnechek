package com.shacomiro.makeabook.domain.ebook.dto;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EbookRequestDto {
	List<FileDto> files;
	String uploader;
	EbookType ebookType;

	public EbookRequestDto(byte[] txtBytes, String txtOriginalFilename, String uploader, EbookType ebookType) {
		this(List.of(new FileDto(new ByteArrayInputStream(txtBytes), txtOriginalFilename,
				"." + FilenameUtils.getExtension(txtOriginalFilename))), uploader, ebookType);
	}
}
