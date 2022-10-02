package com.shacomiro.makeabook.ebook;

import com.shacomiro.makeabook.ebook.extention.epub2.Epub2Translator;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class EbookManager {
    Epub2Translator epub2Translator;

    public EbookManager() {
        this.epub2Translator = new Epub2Translator();
    }

    public Optional<InputStream> translateTxtToEpub2(InputStream inputStream, String fileName) throws IOException {
        ByteArrayOutputStream baos = getByteArrayOutputStream(inputStream);

        return epub2Translator.createEpub2(baos, getEncoding(baos), fileName);
    }

    private ByteArrayOutputStream getByteArrayOutputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        inputStream.transferTo(baos);

        return baos;
    }

    private String getEncoding(ByteArrayOutputStream baos) throws IOException {
        return UniversalDetector.detectCharset(new ByteArrayInputStream(baos.toByteArray()));
    }
}
