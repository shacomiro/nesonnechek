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
import com.shacomiro.makeabook.ebook.error.FileIOException;
import com.shacomiro.makeabook.ebook.grammar.EbookGrammar;

public class IOUtil {
	private IOUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static void readStream(InputStream is, String encoding, List<Section> sectionList,
			Map<String, String> metainfo) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding))) {
			Section section = new Section();
			List<String> paragraphList = new ArrayList<>();
			boolean isNonSectionEbook = true;

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
		} catch (IOException e) {
			throw new FileIOException("fail to read text file", e);
		}
	}

	private static void updateSectionList(List<Section> sectionList, Section section, List<String> paragraphList) {
		section.updateParagraphList(paragraphList);
		sectionList.add(section);
	}

	public static InputStream loadFileToInputStream(Path path) {
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new FileIOException("fail to load InputStream of file '" + path.getFileName() + "'", e);
		}
	}

	public static OutputStream loadFileToOutputStream(Path path) {
		try {
			return Files.newOutputStream(path);
		} catch (IOException e) {
			throw new FileIOException("fail to load OutputStream of file '" + path.getFileName() + "'", e);
		}
	}

	public static void createDirectory(Path path) {
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				throw new FileIOException(
						"fail to create directory '" + path.toAbsolutePath().normalize().toString() + "'", e);
			}
		}
	}

	public static void deleteDirectory(Path path) {
		if (Files.exists(path)) {
			try {
				List<Path> dirListPath = Files.list(path).collect(Collectors.toList());

				if (!dirListPath.isEmpty()) {
					for (Path p : dirListPath) {
						if (Files.isDirectory(p)) {
							deleteDirectory(Paths.get(p.toString(), File.separatorChar + p.getFileName().toString()));
						} else {
							Files.delete(p);
						}
					}
				}
				Files.delete(path);
			} catch (IOException e) {
				throw new FileIOException("fail to delete directory '" + path + "'", e);
			}
		}
	}

	public static void deleteFile(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			throw new FileIOException("fail to delete file '" + path.getFileName() + "'", e);
		}
	}
}
