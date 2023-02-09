package com.shacomiro.makeabook.domain.global.config.ebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.epub.EpubManager;

@Configuration
public class EbookConfiguration {

	@Bean
	public EpubManager epubManager(@Value("${ext-config.ebook.content-directory}") String contentDir,
			@Value("${ext-config.ebook.ebook-directory}") String ebookDir) throws IOException {
		Files.createDirectories(Paths.get(contentDir));
		Files.createDirectories(Paths.get(ebookDir));

		return new EpubManager(contentDir, ebookDir);
	}
}
