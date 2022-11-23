package com.shacomiro.makeabook.api.global.config.formatter;

import java.util.Locale;

import org.springframework.format.Formatter;

import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookFileExtension;

public class EbookExtensionFormatter implements Formatter<EbookFileExtension> {

	@Override
	public EbookFileExtension parse(String text, Locale locale) {
		return text.isEmpty() ? null : EbookFileExtension.valueOf(text.trim().toUpperCase());
	}

	@Override
	public String print(EbookFileExtension ebookFileExtension, Locale locale) {
		return ebookFileExtension.toString().toLowerCase();
	}
}
