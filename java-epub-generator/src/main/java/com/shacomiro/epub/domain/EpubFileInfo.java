package com.shacomiro.epub.domain;

import java.nio.file.Path;

public class EpubFileInfo {
	private String filename;
	private Path filePath;

	public EpubFileInfo(String filename, Path filePath) {
		this.filename = filename;
		this.filePath = filePath;
	}

	public String getFilename() {
		return filename;
	}

	public Path getFilePath() {
		return filePath;
	}

	@Override
	public String toString() {
		return "EpubFileInfo{" +
				"fileName='" + filename + '\'' +
				", filePath=" + filePath +
				'}';
	}
}
