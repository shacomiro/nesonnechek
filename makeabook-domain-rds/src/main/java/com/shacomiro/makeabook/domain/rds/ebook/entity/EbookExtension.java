package com.shacomiro.makeabook.domain.rds.ebook.entity;

public enum EbookExtension {
	EPUB("epub");

	private final String extension;

	EbookExtension(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}
}
