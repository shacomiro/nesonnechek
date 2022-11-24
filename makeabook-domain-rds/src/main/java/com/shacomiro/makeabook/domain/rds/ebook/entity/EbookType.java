package com.shacomiro.makeabook.domain.rds.ebook.entity;

public enum EbookType {
	EPUB2("epub2");

	private final String type;

	EbookType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}