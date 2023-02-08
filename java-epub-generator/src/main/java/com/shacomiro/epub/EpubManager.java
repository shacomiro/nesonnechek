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
import com.shacomiro.epub.exception.FileIOException;
import com.shacomiro.epub.extention.epub2.Epub2Translator;

public class EpubManager {
	private final String contentDir;
	private final String ebookDir;
	private final Epub2Translator epub2Translator;

	public EpubManager(String contentDir, String ebookDir) {
		if (!Files.exists(Paths.get(contentDir)) || !Files.exists(Paths.get(ebookDir))) {
			throw new FileIOException("Content directory and ebook directory must be exists");
		}
		this.contentDir = contentDir;
		this.ebookDir = ebookDir;
		this.epub2Translator = new Epub2Translator(contentDir, ebookDir);
	}

	public EpubFileInfo translateTxtToEpub2(String uuid, ContentTempFileInfo contentTempFileInfo) {
		return epub2Translator.createEpub2(uuid, contentTempFileInfo.getOriginalTempFilename(),
				readTxtAllLines(contentTempFileInfo.getTempFilePath()));
	}

	public Path getEpubFilePath(String ebookExtension, String fileName) {
		return Paths.get(ebookDir, File.separatorChar + ebookExtension + File.separatorChar + fileName);
	}

	private List<String> readTxtAllLines(Path path) {
		try {
			return Files.readAllLines(path, Charset.forName(getEncoding(path)));
		} catch (IOException e) {
			throw new FileIOException("I/O error occurred when reading lines from file", e);
		}
	}

	private String getEncoding(Path path) {
		Optional<String> charset;

		try (InputStream is = Files.newInputStream(path)) {
			charset = Optional.ofNullable(UniversalDetector.detectCharset(is));
		} catch (IOException e) {
			throw new FileIOException("I/O error occurred when detecting charset", e);
		}

		return charset
				.orElseThrow(() -> new NullPointerException("Could not detect charset"));
	}

	public String getContentDir() {
		return contentDir;
	}

	public String getEbookDir() {
		return ebookDir;
	}
}
