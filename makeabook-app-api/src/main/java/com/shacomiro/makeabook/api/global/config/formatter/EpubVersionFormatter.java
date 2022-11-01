package com.shacomiro.makeabook.api.global.config.formatter;

import java.util.Locale;

import org.springframework.format.Formatter;

import com.shacomiro.makeabook.api.ebook.domain.EpubVersion;

public class EpubVersionFormatter implements Formatter<EpubVersion> {

	@Override
	public EpubVersion parse(String text, Locale locale) {
		return text.isEmpty() ? null : EpubVersion.valueOf(text.trim().toUpperCase());
	}

	@Override
	public String print(EpubVersion epubVersion, Locale locale) {
		return epubVersion.toString().toLowerCase();
	}
}
