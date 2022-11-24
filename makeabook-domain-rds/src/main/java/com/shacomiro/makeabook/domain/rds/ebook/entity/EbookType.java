package com.shacomiro.makeabook.domain.rds.ebook.entity;

public enum EbookType {
	EPUB2("epub2");

	private final String value;

	EbookType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}