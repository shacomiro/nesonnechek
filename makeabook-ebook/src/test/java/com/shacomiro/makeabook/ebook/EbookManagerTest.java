package com.shacomiro.makeabook.ebook;

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
import com.shacomiro.makeabook.ebook.error.EmptyFileException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EbookManagerTest {
	static final String testFilePath = "./makeabook-ebook/src/test/resources";
	static final String mainFilePath = "./makeabook-ebook/src/main/resources/file";

	InputStream loadTestFile(String fileName) throws FileNotFoundException {
		return new FileInputStream(
				Paths.get(testFilePath, File.separatorChar + fileName).toAbsolutePath().normalize().toString());
	}

	@Test
	@DisplayName("Ebook 파일 생성(올바른 파일)")
	void createBookByCorrectFileTest() throws IOException {
		//given
		String fileName = "애국가.txt";
		Path expectTestResultPath = Paths.get(mainFilePath,
						File.separatorChar + "애국가" + File.separatorChar + "애국가.epub")
				.toAbsolutePath()
				.normalize();
		InputStream is = loadTestFile(fileName);

		//when
		EbookManager ebookManager = new EbookManager();
		EpubFileInfo info = ebookManager.translateTxtToEpub2(is, fileName);
		is.close();

		//then
		Assertions.assertEquals(expectTestResultPath, info.getFilePath());
	}

	@Test
	@DisplayName("Ebook 파일 생성(빈 파일)")
	void createEbookByWorngFileTest() throws IOException {
		//given
		String fileName = "빈파일.txt";
		InputStream is = loadTestFile(fileName);

		//when
		EbookManager ebookManager = new EbookManager();

		//then
		Assertions.assertThrows(EmptyFileException.class, () -> ebookManager.translateTxtToEpub2(is, fileName));
		is.close();
	}

}