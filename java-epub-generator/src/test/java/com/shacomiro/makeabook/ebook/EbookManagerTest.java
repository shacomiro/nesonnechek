package com.shacomiro.makeabook.ebook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import com.shacomiro.epub.EpubManager;
import com.shacomiro.epub.domain.ContentTempFileInfo;
import com.shacomiro.epub.domain.EpubFileInfo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EbookManagerTest {
	static final String testFilePath = "./src/test/resources";

	@Test
	@Order(1)
	@DisplayName("Ebook 파일 생성(올바른 파일)")
	void createBookByCorrectFileTest() {
		//given
		String uuid = UUID.randomUUID().toString();
		String fileName = "애국가.txt";
		Path expectTestResultPath = Paths.get(testFilePath,
						File.separatorChar + "ebook" + File.separatorChar + "epub2" + File.separatorChar + uuid + ".epub")
				.toAbsolutePath()
				.normalize();
		ContentTempFileInfo contentTempFileInfo = new ContentTempFileInfo(uuid,
				Paths.get(testFilePath, File.separatorChar + fileName).normalize().toAbsolutePath(), fileName);

		//when
		EpubManager ebookManager = new EpubManager(testFilePath);
		EpubFileInfo info = ebookManager.translateTxtToEpub2(uuid, fileName, contentTempFileInfo);

		//then
		Assertions.assertEquals(expectTestResultPath, info.getFilePath());
		new File(info.getFilePath().toUri()).deleteOnExit();
	}

	@Test
	@Order(2)
	@DisplayName("Ebook 파일 생성(빈 파일)")
	void createEbookByEmptyFileTest() {
		//given
		String uuid = UUID.randomUUID().toString();
		String fileName = "빈파일.txt";
		ContentTempFileInfo contentTempFileInfo = new ContentTempFileInfo(uuid,
				Paths.get(testFilePath, File.separatorChar + fileName).normalize().toAbsolutePath(), fileName);

		//when
		EpubManager ebookManager = new EpubManager(testFilePath);

		//then
		Assertions.assertThrows(NullPointerException.class,
				() -> ebookManager.translateTxtToEpub2(uuid, fileName, contentTempFileInfo));
	}

}
