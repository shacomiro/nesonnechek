package com.shacomiro.makeabook.ebook.domain;

import java.nio.file.Path;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EpubFileInfo {
	private String fileName;
	private Path filePath;

	@Builder
	public EpubFileInfo(String fileName, Path filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}
}
