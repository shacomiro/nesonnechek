package com.shacomiro.makeabook.ebook.extention.epub2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class Epub2Translator {

    public Optional<InputStream> createEpub2(ByteArrayOutputStream baos, String encoding, String fileName) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray()), encoding));

            while(br.ready()) {
                String str = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<InputStream> result = Optional.ofNullable(loadFileToInputStream(fileName));
        removeFile(fileName);

        return result;
    }

    private void createSection() {

    }

    private void createContent() {

    }

    private Path getPath(String fileName) {
        return Paths.get(File.separatorChar + "file", File.separatorChar + fileName);
    }

    public InputStream loadFileToInputStream(String fileName) {
        return getClass().getResourceAsStream(getPath(fileName).toString());
    }

    public void removeFile(String fileName) {
        Path filePath = Paths.get(File.separatorChar + "file", File.separatorChar + fileName);
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
