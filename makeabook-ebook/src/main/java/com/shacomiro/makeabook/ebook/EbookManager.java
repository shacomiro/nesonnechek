package com.shacomiro.makeabook.ebook;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

	public EpubFileInfo translateTxtToEpub2(InputStream inputStream, String fileName) throws IOException {
		if (inputStream.available() == 0) {
			throw new EmptyFileException(fileName + " is empty file");
		}

		ByteArrayOutputStream baos = getByteArrayOutputStream(inputStream);
		EpubFileInfo fileInfo = epub2Translator.createEpub2(baos, getEncoding(baos), fileName);
		baos.close();

		return fileInfo;
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
