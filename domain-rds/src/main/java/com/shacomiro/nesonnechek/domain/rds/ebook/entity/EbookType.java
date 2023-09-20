package com.shacomiro.nesonnechek.domain.rds.ebook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EbookType {
	EPUB2("epub2", ".epub", "application/epub+zip");

	private final String value;
	private final String extension;
	private final String contentType;
}
