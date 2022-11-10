package com.shacomiro.makeabook.ebook.extention.epub2;

import static com.shacomiro.makeabook.core.util.IOUtil.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Epub2TranslatorTest {
	static final String testFilePath = "./src/test/resources";

	@Test
	@DisplayName("생성된 epub2 파일 Path 반환")
	void createEpub2Test() {
		//given
		String uuid = UUID.randomUUID().toString();
		String fileName = "애국가.txt";
		Path expectTestResultPath = Paths.get(testFilePath,
						File.separatorChar + "ebook" + File.separatorChar + "epub2" + File.separatorChar + uuid + ".epub")
				.toAbsolutePath()
				.normalize();
		List<String> lines = new ArrayList<>();
		lines.add("*BT*애국가");
		lines.add("*BA*안익태");
		lines.add("*ST*1절");
		lines.add("동해 물과 백두산이");
		lines.add("마르고 닳도록");
		lines.add("하느님이 보우하사");
		lines.add("우리나라 만세");

		createDirectory(Paths.get(testFilePath, File.separatorChar + "contents"));
		createDirectory(Paths.get(testFilePath, File.separatorChar + "ebook"));

		//when
		Epub2Translator epub2Translator = new Epub2Translator(testFilePath + "/contents", testFilePath + "/ebook");
		EpubFileInfo info = epub2Translator.createEpub2(uuid, fileName, lines);

		//then
		Assertions.assertEquals(expectTestResultPath, info.getFilePath());
		new File(info.getFilePath().toUri()).deleteOnExit();
	}
}