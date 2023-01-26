package com.shacomiro.epub.domain;

import java.nio.file.Path;

public class ContentTempFileInfo {
	private final String uuid;
	private final Path tempFilePath;
	private final String originalTempFilename;

	public ContentTempFileInfo(String uuid, Path tempFilePath, String originalTempFilename) {
		this.uuid = uuid;
		this.tempFilePath = tempFilePath;
		this.originalTempFilename = originalTempFilename;
	}

	public String getUuid() {
		return uuid;
	}

	public Path getTempFilePath() {
		return tempFilePath;
	}

	public String getOriginalTempFilename() {
		return originalTempFilename;
	}
}
