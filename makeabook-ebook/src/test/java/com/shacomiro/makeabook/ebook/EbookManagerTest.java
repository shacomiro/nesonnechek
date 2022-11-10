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

import com.shacomiro.makeabook.ebook.domain.ContentTempFileInfo;
import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;

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
		ContentTempFileInfo contentTempFileInfo = ContentTempFileInfo.builder()
				.txtTempFilePath(Paths.get(testFilePath, File.separatorChar + fileName).normalize().toAbsolutePath())
				.build();

		//when
		EbookManager ebookManager = new EbookManager(testFilePath);
		EpubFileInfo info = ebookManager.translateTxtToEpub2(uuid, fileName, contentTempFileInfo);

		//then
		Assertions.assertEquals(expectTestResultPath, info.getFilePath());
		new File(info.getFilePath().toUri()).deleteOnExit();
	}

	@Test
	@Order(2)
	@DisplayName("Ebook 파일 생성(빈 파일)")
	void createEbookByWorngFileTest() {
		//given
		String uuid = UUID.randomUUID().toString();
		String fileName = "빈파일.txt";
		ContentTempFileInfo contentTempFileInfo = ContentTempFileInfo.builder()
				.txtTempFilePath(Paths.get(testFilePath, File.separatorChar + fileName).normalize().toAbsolutePath())
				.build();

		//when
		EbookManager ebookManager = new EbookManager(testFilePath);

		//then
		Assertions.assertThrows(NullPointerException.class,
				() -> ebookManager.translateTxtToEpub2(uuid, fileName, contentTempFileInfo));
	}

}