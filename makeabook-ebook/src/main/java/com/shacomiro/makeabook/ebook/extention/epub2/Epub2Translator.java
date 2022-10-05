package com.shacomiro.makeabook.ebook.extention.epub2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import com.shacomiro.makeabook.ebook.extention.epub2.domain.Section;
import com.shacomiro.makeabook.ebook.grammar.EbookGrammar;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;

public class Epub2Translator {

	public Optional<InputStream> createEpub2(ByteArrayOutputStream baos, String encoding, String fileName) {
		Book book = new Book();
		List<Section> sectionList = new ArrayList<>();

		readStream(baos, encoding, book, sectionList);

		for (Section section : sectionList) {

		}

		return null;
	}

	private void readStream(ByteArrayOutputStream baos, String encoding, Book book, List<Section> sectionList) {
		Metadata metadata = book.getMetadata();
		Section section = new Section();
		List<String> paragraphList = new ArrayList<>();
		boolean isNonSectionEbook = true;

		try {
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
					metadata.addTitle(StringUtils.split(str, EbookGrammar.BOOK_TITLE)[0]);
				}

				if (StringUtils.contains(str, EbookGrammar.BOOK_AUTHOR)) {
					isParagraph = false;
					metadata.addAuthor(new Author(StringUtils.split(str, EbookGrammar.BOOK_AUTHOR)[0]));
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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void updateSectionList(List<Section> sectionList, Section section, List<String> paragraphList) {
		section.updateParagraphList(paragraphList);
		sectionList.add(section);
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
