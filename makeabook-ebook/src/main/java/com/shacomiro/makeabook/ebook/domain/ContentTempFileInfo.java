package com.shacomiro.makeabook.ebook.domain;

import java.nio.file.Path;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ContentTempFileInfo {
	private final Path txtTempFilePath;

	@Builder
	public ContentTempFileInfo(Path txtTempFilePath) {
		this.txtTempFilePath = txtTempFilePath;
	}
}
