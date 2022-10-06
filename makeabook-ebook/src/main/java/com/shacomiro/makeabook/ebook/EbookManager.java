package com.shacomiro.makeabook.ebook;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.mozilla.universalchardet.UniversalDetector;

import com.shacomiro.makeabook.ebook.extention.epub2.Epub2Translator;

public class EbookManager {
	Epub2Translator epub2Translator;

	public EbookManager() {
		this.epub2Translator = new Epub2Translator();
	}

	public Optional<InputStream> translateTxtToEpub2(InputStream inputStream, String fileName) throws IOException {
		ByteArrayOutputStream baos = getByteArrayOutputStream(inputStream);

		return epub2Translator.createEpub2(baos, getEncoding(baos), fileName);
	}

	private String getEncoding(ByteArrayOutputStream baos) throws IOException {
		return UniversalDetector.detectCharset(new ByteArrayInputStream(baos.toByteArray()));
	}
}
