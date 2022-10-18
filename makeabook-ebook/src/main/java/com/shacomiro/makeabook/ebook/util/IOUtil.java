package com.shacomiro.makeabook.ebook.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.shacomiro.makeabook.ebook.extention.epub2.domain.Section;
import com.shacomiro.makeabook.ebook.grammar.EbookGrammar;

public class IOUtil {
	private static final String BASE_PATH = "./makeabook-ebook/src/main/resources/file";

	public static ByteArrayOutputStream getByteArrayOutputStream(InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		inputStream.transferTo(baos);

		return baos;
	}

	public static void readStream(ByteArrayOutputStream baos, String encoding, List<Section> sectionList,
			Map<String, String> metainfo) throws
			IOException {
		Section section = new Section();
		List<String> paragraphList = new ArrayList<>();
		boolean isNonSectionEbook = true;

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(baos.toByteArray()), encoding));

		while (br.ready()) {
			String str = br.readLine();
			boolean isParagraph = true;

			if (StringUtils.isEmpty(str)) {
				continue;
			}

			if (StringUtils.contains(str, EbookGrammar.BOOK_TITLE)) {
				isParagraph = false;
				metainfo.put("Title", StringUtils.split(str, EbookGrammar.BOOK_TITLE)[0]);
			}

			if (StringUtils.contains(str, EbookGrammar.BOOK_AUTHOR)) {
				isParagraph = false;
				metainfo.put("Author", StringUtils.split(str, EbookGrammar.BOOK_AUTHOR)[0]);
			}

			if (StringUtils.contains(str, EbookGrammar.SECTION_TITLE)) {
				isParagraph = false;

				if (isNonSectionEbook) {
					isNonSectionEbook = false;
				} else {
					updateSectionList(sectionList, section, paragraphList);
				}

				paragraphList = new ArrayList<>();
				section = Section.builder()
						.title(StringUtils.split(str, EbookGrammar.SECTION_TITLE)[0])
						.build();
			}

			if (isParagraph) {
				paragraphList.add(str);
			}
		}
		updateSectionList(sectionList, section, paragraphList);

		br.close();
	}

	private static void updateSectionList(List<Section> sectionList, Section section, List<String> paragraphList) {
		section.updateParagraphList(paragraphList);
		sectionList.add(section);
	}

	public static Path getDirPath(String dirName) {
		return Paths.get(BASE_PATH, File.separatorChar + dirName).toAbsolutePath().normalize();
	}

	public static Path getFilePath(String dirName, String fileName) {
		return Paths.get(getDirPath(dirName).toString(), File.separatorChar + fileName).toAbsolutePath().normalize();
	}

	public static InputStream loadFileToInputStream(String dirName, String fileName) throws FileNotFoundException {
		return new FileInputStream(getFilePath(dirName, fileName).toString());
	}

	public static void createDirectoryIfNotExists(String dirName) {
		File file = getDirPath(dirName).toFile();

		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void deleteDirectoryIfExists(String dirName) {
		File directory = new File(getDirPath(dirName).toString());

		if (directory.exists()) {
			File[] dirList = directory.listFiles();

			for (int i = 0; i < dirList.length; i++) {
				if (dirList[i].isFile()) {
					dirList[i].delete();
				} else {
					deleteDirectoryIfExists(dirList[i].getPath());
				}
				dirList[i].delete();
			}
			directory.delete();
		}
	}

	public static void deleteFile(String dirName, String fileName) throws IOException {
		Files.delete(getFilePath(dirName, fileName));
	}
}
