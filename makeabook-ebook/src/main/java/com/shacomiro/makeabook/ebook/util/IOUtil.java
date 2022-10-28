package com.shacomiro.makeabook.ebook.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.shacomiro.makeabook.ebook.domain.Section;
import com.shacomiro.makeabook.ebook.grammar.EbookGrammar;

public class IOUtil {
	private static final String BASE_PATH = "./makeabook-ebook/src/main/resources/file";

	private IOUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void readStream(InputStream is, String encoding, List<Section> sectionList,
			Map<String, String> metainfo) throws IOException {
		Section section = new Section();
		List<String> paragraphList = new ArrayList<>();
		boolean isNonSectionEbook = true;

		InputStreamReader isr = new InputStreamReader(is, encoding);
		BufferedReader br = new BufferedReader(isr);

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
		isr.close();
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

	public static InputStream loadFileToInputStream(String dirName, String fileName) throws IOException {
		return Files.newInputStream(getFilePath(dirName, fileName));
	}

	public static OutputStream loadFileToOutputStream(String dirName, String fileName) throws IOException {
		return Files.newOutputStream(getFilePath(dirName, fileName));
	}

	public static void createDirectory(String dirName) throws IOException {
		Path targetPath = getDirPath(dirName);

		if (Files.exists(targetPath)) {
			deleteDirectory(dirName);
		}
		Files.createDirectory(targetPath);
	}

	public static void deleteDirectory(String dirName) throws IOException {
		Path targetPath = getDirPath(dirName);

		if (Files.exists(targetPath)) {
			List<Path> dirListPath = Files.list(targetPath).collect(Collectors.toList());

			if (!dirListPath.isEmpty()) {
				for (Path path : dirListPath) {
					if (Files.isDirectory(path)) {
						deleteDirectory(dirName + File.separatorChar + path.getFileName());
					} else {
						Files.delete(path);
					}
				}
			}
			Files.delete(targetPath);
		}
	}

	public static void deleteFile(String dirName, String fileName) throws IOException {
		Files.delete(getFilePath(dirName, fileName));
	}
}
