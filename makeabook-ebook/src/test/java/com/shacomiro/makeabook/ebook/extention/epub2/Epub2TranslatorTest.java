package com.shacomiro.makeabook.ebook.extention.epub2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Epub2TranslatorTest {
	static final String testFilePath = "./makeabook-ebook/src/test/resources";
	static final String mainFilePath = "./makeabook-ebook/src/main/resources/file";

	InputStream loadTestFile(String fileName) throws FileNotFoundException {
		return new FileInputStream(
				Paths.get(testFilePath, File.separatorChar + fileName).toAbsolutePath().normalize().toString());
	}

	@Test
	@DisplayName("생성된 epub2 파일 Path 반환")
	void createEpub2Test() throws IOException {
		//given
		String fileName = "애국가.txt";
		Path expectTestResultPath = Paths.get(mainFilePath,
						File.separatorChar + "애국가" + File.separatorChar + "애국가.epub")
				.toAbsolutePath()
				.normalize();

		InputStream is = loadTestFile(fileName);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		is.transferTo(baos);
		is.close();

		//when
		Epub2Translator epub2Translator = new Epub2Translator();
		Path path = epub2Translator.createEpub2(baos, "utf-8", fileName);
		baos.close();

		//then
		Assertions.assertEquals(expectTestResultPath, path);
	}
}