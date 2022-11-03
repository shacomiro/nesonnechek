package com.shacomiro.makeabook.ebook;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.mozilla.universalchardet.UniversalDetector;

import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.error.FileIOException;
import com.shacomiro.makeabook.ebook.extention.epub2.Epub2Translator;

public class EbookManager {
	private final String contentsBasePath;
	private final String ebookBasePath;
	private final Epub2Translator epub2Translator;

	public EbookManager(String resourcesDir) {
		createDirectory(Paths.get(resourcesDir));
		this.contentsBasePath = resourcesDir + "/contents";
		this.ebookBasePath = resourcesDir + "/ebook";
		initBaseDirectory();
		this.epub2Translator = new Epub2Translator(contentsBasePath, ebookBasePath);
	}

	public EpubFileInfo translateTxtToEpub2(byte[] bytes, String fileName) {
		return epub2Translator.createEpub2(bytes, getEncoding(bytes), fileName);
	}

	public Path getEpubFilePath(String ebookExtension, String fileName) {
		return Paths.get(ebookBasePath, File.separatorChar + ebookExtension + File.separatorChar + fileName);
	}

	private String getEncoding(byte[] bytes) {
		Optional<String> charset;

		try (InputStream is = new ByteArrayInputStream(bytes)) {
			charset = Optional.ofNullable(UniversalDetector.detectCharset(is));
		} catch (IOException e) {
			throw new FileIOException("Fail to detect charset", e);
		}

		return charset
				.orElseThrow(() -> new NullPointerException("Could not detect charset"));
	}

	private void initBaseDirectory() {
		createDirectory(Paths.get(contentsBasePath));
		createDirectory(Paths.get(ebookBasePath));
	}
}
