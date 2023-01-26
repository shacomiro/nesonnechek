package com.shacomiro.makeabook.domain.global.config.ebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.epub.EbookManager;

@Configuration
public class EbookConfiguration {

	@Bean
	public EbookManager ebookManager(@Value("${ext-config.ebook.resource-path}") String resourcePath) {
		return new EbookManager(resourcePath);
	}
}
