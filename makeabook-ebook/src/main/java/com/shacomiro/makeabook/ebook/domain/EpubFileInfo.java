package com.shacomiro.makeabook.ebook.domain;

import java.nio.file.Path;

public class EpubFileInfo {
	private String fileName;
	private Path filePath;

	public EpubFileInfo(String fileName, Path filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public Path getFilePath() {
		return filePath;
	}

	@Override
	public String toString() {
		return "EpubFileInfo{" +
				"fileName='" + fileName + '\'' +
				", filePath=" + filePath +
				'}';
	}
}
