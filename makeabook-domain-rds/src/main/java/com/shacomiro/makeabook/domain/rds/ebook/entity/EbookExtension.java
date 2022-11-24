package com.shacomiro.makeabook.domain.rds.ebook.entity;

public enum EbookExtension {
	EPUB("epub");

	private final String value;

	EbookExtension(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
