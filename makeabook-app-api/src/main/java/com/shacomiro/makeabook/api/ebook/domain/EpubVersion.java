package com.shacomiro.makeabook.api.ebook.domain;

public enum EpubVersion {
	EPUB2("epub2");

	private final String epubVer;

	EpubVersion(String epubVer) {
		this.epubVer = epubVer;
	}

	public String getEpubVersion() {
		return epubVer;
	}
}
