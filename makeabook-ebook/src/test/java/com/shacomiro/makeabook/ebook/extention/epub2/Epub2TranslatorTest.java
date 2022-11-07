package com.shacomiro.makeabook.ebook.extention.epub2;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

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

import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Epub2TranslatorTest {
	static final String testFilePath = "./src/test/resources";

	InputStream loadTestFile(String fileName) throws FileNotFoundException {
		return new FileInputStream(
				Paths.get(testFilePath, File.separatorChar + fileName).toAbsolutePath().normalize().toString());
	}

	@Test
	@DisplayName("생성된 epub2 파일 Path 반환")
	void createEpub2Test() throws IOException {
		//given
		String fileName = "애국가.txt";
		Path expectTestResultPath = Paths.get(testFilePath,
						File.separatorChar + "ebook" + File.separatorChar + "epub2" + File.separatorChar + "애국가.epub")
				.toAbsolutePath()
				.normalize();

		InputStream is = loadTestFile(fileName);
		createDirectory(Paths.get(testFilePath, File.separatorChar + "contents"));
		createDirectory(Paths.get(testFilePath, File.separatorChar + "ebook"));

		//when
		Epub2Translator epub2Translator = new Epub2Translator(testFilePath + "/contents", testFilePath + "/ebook");
		EpubFileInfo info = epub2Translator.createEpub2(is.readAllBytes(), "utf-8", fileName);
		is.close();

		//then
		Assertions.assertEquals(expectTestResultPath, info.getFilePath());
	}
}