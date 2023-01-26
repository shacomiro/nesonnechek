package com.shacomiro.epub;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.mozilla.universalchardet.UniversalDetector;

import com.shacomiro.epub.domain.ContentTempFileInfo;
import com.shacomiro.epub.domain.EpubFileInfo;
import com.shacomiro.epub.extention.epub2.Epub2Translator;

public class EbookManager {
	private final String contentsBasePath;
	private final String ebookBasePath;
	private final Epub2Translator epub2Translator;

	public EbookManager(String resourcesDir) {
		Path resourcePath = Paths.get(resourcesDir);
		try {
			if (!Files.exists(resourcePath)) {
				Files.createDirectory(resourcePath);
			}
		} catch (IOException e) {
			throw new RuntimeException("Fail to create directory '" + resourcePath.toAbsolutePath().normalize() + "'", e);
		}
		this.contentsBasePath = resourcesDir + "/contents";
		this.ebookBasePath = resourcesDir + "/ebook";
		initBaseDirectory();
		this.epub2Translator = new Epub2Translator(contentsBasePath, ebookBasePath);
	}

	public EpubFileInfo translateTxtToEpub2(String uuid, String fileName, ContentTempFileInfo contentTempFileInfo) {
		return epub2Translator.createEpub2(uuid, fileName, readTxtAllLines(contentTempFileInfo.getTempFilePath()));
	}

	public Path getEpubFilePath(String ebookExtension, String fileName) {
		return Paths.get(ebookBasePath, File.separatorChar + ebookExtension + File.separatorChar + fileName);
	}

	private List<String> readTxtAllLines(Path path) {
		try {
			return Files.readAllLines(path, Charset.forName(getEncoding(path)));
		} catch (IOException e) {
			throw new RuntimeException("Fail to read string from file", e);
		}
	}

	private String getEncoding(Path path) {
		Optional<String> charset;

		try (InputStream is = Files.newInputStream(path)) {
			charset = Optional.ofNullable(UniversalDetector.detectCharset(is));
		} catch (IOException e) {
			throw new RuntimeException("Fail to detect charset", e);
		}

		return charset
				.orElseThrow(() -> new NullPointerException("Could not detect charset"));
	}

	private void initBaseDirectory() {
		Path contentPath = Paths.get(contentsBasePath);
		Path ebookPath = Paths.get(ebookBasePath);

		try {
			if (!Files.exists(contentPath)) {
				Files.createDirectory(contentPath);
			}
			if (!Files.exists(ebookPath)) {
				Files.createDirectory(ebookPath);
			}
		} catch (IOException e) {
			throw new RuntimeException("Parent directory of epub2 does not exist", e);
		}
	}

	public String getContentsBasePath() {
		return contentsBasePath;
	}

	public String getEbookBasePath() {
		return ebookBasePath;
	}
}
