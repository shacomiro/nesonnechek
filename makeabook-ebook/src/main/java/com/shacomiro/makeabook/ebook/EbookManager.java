package com.shacomiro.makeabook.ebook;

import com.shacomiro.makeabook.ebook.extention.epub2.Epub2Generator;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class EbookManager {
    Epub2Generator epub2Generator;

    public EbookManager() {
        this.epub2Generator = new Epub2Generator();
    }

    public Optional<InputStream> convertTxtToEpub2(InputStream inputStream, String fileName) throws IOException {
        ByteArrayOutputStream baos = getByteArrayOutputStream(inputStream);

        return epub2Generator.createEpub2(baos, getEncoding(baos), fileName);
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
