package com.shacomiro.epub.domain;

import java.nio.file.Path;

public class EpubFileInfo {
	private final String uuid;
	private final String filename;
	private final Path filePath;

	public EpubFileInfo(String uuid, String filename, Path filePath) {
		this.uuid = uuid;
		this.filename = filename;
		this.filePath = filePath;
	}

	public String getUuid() {
		return uuid;
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
				"uuid='" + uuid + '\'' +
				", filename='" + filename + '\'' +
				", filePath=" + filePath +
				'}';
	}
}
