package com.shacomiro.makeabook.domain.ebook.dto;

import java.io.ByteArrayInputStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
	ByteArrayInputStream fileBais;
	String originalFilename;
	String extension;
}
