package com.shacomiro.makeabook.domain.rds.ebook.entity;

public enum EbookExtension {
	EPUB2("epub2");

	private final String ebookExt;

	EbookExtension(String ebookExt) {
		this.ebookExt = ebookExt;
	}

	public String getEbookExt() {
		return ebookExt;
	}
}