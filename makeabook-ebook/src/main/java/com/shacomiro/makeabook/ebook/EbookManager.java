package com.shacomiro.makeabook.ebook;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;
import org.mozilla.universalchardet.UniversalDetector;

import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.error.EmptyFileException;
import com.shacomiro.makeabook.ebook.extention.epub2.Epub2Translator;

public class EbookManager {
	Epub2Translator epub2Translator;

	public EbookManager() {
		this.epub2Translator = new Epub2Translator();
	}

	public EpubFileInfo translateTxtToEpub2(byte[] bytes, String fileName) {
		if (bytes.length == 0) {
			throw new EmptyFileException(fileName + " is empty file");
		}

		return epub2Translator.createEpub2(bytes, getEncoding(bytes), fileName);
	}

	public Path getExistEpubFilePath(String fileName) {
		return getFilePath(FilenameUtils.removeExtension(fileName), fileName);
	}

	public void deleteRemainFiles(String fileName) {
		deleteDirectory(FilenameUtils.removeExtension(fileName));
	}

	private String getEncoding(byte[] bytes) {
		String charset = null;

		try (InputStream is = new ByteArrayInputStream(bytes)) {
			charset = UniversalDetector.detectCharset(is);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return charset;
	}
}
