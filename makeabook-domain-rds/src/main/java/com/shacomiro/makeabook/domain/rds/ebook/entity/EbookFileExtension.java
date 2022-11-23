package com.shacomiro.makeabook.domain.rds.ebook.entity;

public enum EbookFileExtension {
	EPUB2("epub2");

	private final String ebookExt;

	EbookFileExtension(String ebookExt) {
		this.ebookExt = ebookExt;
	}

	public String getEbookExt() {
		return ebookExt;
	}
}