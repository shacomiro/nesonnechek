package com.shacomiro.makeabook.ebook;

import static com.shacomiro.makeabook.core.util.IOUtil.*;

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

import com.shacomiro.makeabook.core.util.IOUtil;
import com.shacomiro.makeabook.ebook.domain.ContentTempFileInfo;
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

	public EpubFileInfo translateTxtToEpub2(String uuid, String fileName, ContentTempFileInfo contentTempFileInfo) {
		return epub2Translator.createEpub2(uuid, fileName, readTxtAllLines(contentTempFileInfo.getTxtTempFilePath()));
	}

	public Path getEpubFilePath(String ebookExtension, String fileName) {
		return Paths.get(ebookBasePath, File.separatorChar + ebookExtension + File.separatorChar + fileName);
	}

	private List<String> readTxtAllLines(Path path) {
		return IOUtil.readStringFromFile(path, Charset.forName(getEncoding(path)));
	}

	private String getEncoding(Path path) {
		Optional<String> charset;

		try (InputStream is = Files.newInputStream(path)) {
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

	public String getContentsBasePath() {
		return contentsBasePath;
	}

	public String getEbookBasePath() {
		return ebookBasePath;
	}
}
