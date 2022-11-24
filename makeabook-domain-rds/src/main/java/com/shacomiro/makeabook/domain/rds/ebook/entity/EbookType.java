package com.shacomiro.makeabook.domain.rds.ebook.entity;

public enum EbookType {
	EPUB2("epub2", ".epub");

	private final String value;
	private final String extension;

	EbookType(String value, String extension) {
		this.value = value;
		this.extension = extension;
	}

	public String getValue() {
		return value;
	}

	public String getExtension() {
		return extension;
	}
}