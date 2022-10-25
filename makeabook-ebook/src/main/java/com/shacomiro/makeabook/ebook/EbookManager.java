package com.shacomiro.makeabook.ebook;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;
import org.mozilla.universalchardet.UniversalDetector;

import com.shacomiro.makeabook.ebook.extention.epub2.Epub2Translator;

public class EbookManager {
	Epub2Translator epub2Translator;

	public EbookManager() {
		this.epub2Translator = new Epub2Translator();
	}

	public Path translateTxtToEpub2(InputStream inputStream, String fileName) throws IOException {
		ByteArrayOutputStream baos = getByteArrayOutputStream(inputStream);
		Path resultPath = epub2Translator.createEpub2(baos, getEncoding(baos), fileName);
		baos.close();

		return resultPath;
	}

	public Path getExistEpubFilePath(String fileName) {
		return getFilePath(FilenameUtils.removeExtension(fileName), fileName);
	}

	public void deleteRemainFiles(String fileName) throws IOException {
		deleteDirectoryIfExists(FilenameUtils.removeExtension(fileName));
	}

	private String getEncoding(ByteArrayOutputStream baos) throws IOException {
		return UniversalDetector.detectCharset(new ByteArrayInputStream(baos.toByteArray()));
	}
}
