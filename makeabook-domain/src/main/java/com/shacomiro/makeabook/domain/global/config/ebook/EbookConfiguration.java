package com.shacomiro.makeabook.domain.global.config.ebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.epub.EpubManager;

@Configuration
public class EbookConfiguration {

	@Bean
	public EpubManager epubManager(@Value("${ext-config.ebook.resource-dir}") String resourceDir) {
		return new EpubManager(resourceDir);
	}
}
