package com.shacomiro.makeabook.api.global.config.formatter;

import java.util.Locale;

import org.springframework.format.Formatter;

import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;

public class EbookTypeFormatter implements Formatter<EbookType> {

	@Override
	public EbookType parse(String text, Locale locale) {
		return text.isEmpty() ? null : EbookType.valueOf(text.trim().toUpperCase());
	}

	@Override
	public String print(EbookType ebookType, Locale locale) {
		return ebookType.toString().toLowerCase();
	}
}
