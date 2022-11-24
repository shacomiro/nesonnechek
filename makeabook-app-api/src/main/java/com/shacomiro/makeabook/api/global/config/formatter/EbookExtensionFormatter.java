package com.shacomiro.makeabook.api.global.config.formatter;

import java.util.Locale;

import org.springframework.format.Formatter;

import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookExtension;

public class EbookExtensionFormatter implements Formatter<EbookExtension> {

	@Override
	public EbookExtension parse(String text, Locale locale) {
		return text.isEmpty() ? null : EbookExtension.valueOf(text.trim().toUpperCase());
	}

	@Override
	public String print(EbookExtension ebookExtension, Locale locale) {
		return ebookExtension.toString().toLowerCase();
	}
}
