package com.shacomiro.nesonnechek.domain.global.config.ebook;

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
	public EpubManager epubManager(@Value("${java-epub-generator.directory.content}") String contentDir,
			@Value("${java-epub-generator.directory.ebook}") String ebookDir) throws IOException {
		Files.createDirectories(Paths.get(contentDir));
		Files.createDirectories(Paths.get(ebookDir));

		return new EpubManager(contentDir, ebookDir);
	}
}
