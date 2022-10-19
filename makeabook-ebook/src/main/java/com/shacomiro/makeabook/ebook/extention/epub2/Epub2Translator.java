package com.shacomiro.makeabook.ebook.extention.epub2;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;
import static j2html.TagCreator.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.shacomiro.makeabook.ebook.extention.epub2.domain.Section;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

public class Epub2Translator {

	private static InputStream getResource(String path) {
		return Epub2Translator.class.getResourceAsStream(path);
	}

	private static Resource getResource(String path, String href) throws IOException {
		return new Resource(getResource(path), href);
	}

	public Optional<InputStream> createEpub2(ByteArrayOutputStream baos, String encoding, String fileName) throws
			IOException {
		createDirectoryIfNotExists(fileName);
		String dirPath = getDirPath(fileName).toString();

		List<Section> sectionList = new ArrayList<>();
		Map<String, String> metainfo = new HashMap<>();
		preProcess(baos, encoding, sectionList, metainfo);

		int sectionNum = 0;
		for (Section section : sectionList) {
			sectionNum++;
			createSectionXhtml(section, dirPath, fileName, sectionNum);
		}

		Book book = new Book();
		Metadata metadata = book.getMetadata();

		metadata.addTitle(metainfo.get("Title"));
		metadata.addAuthor(new Author(metainfo.get("Author")));

		String[] xhtmlFileNames = new File(dirPath).list();
		for (int i = 0; i < xhtmlFileNames.length; i++) {
			book.addSection(sectionList.get(i).getTitle(),
					getResource(File.separatorChar + "file" + File.separatorChar + fileName + File.separatorChar
							+ xhtmlFileNames[i], xhtmlFileNames[i]));
		}

		EpubWriter epubWriter = new EpubWriter();
		String bookFileName = metainfo.get("Title") + MediatypeService.EPUB.getDefaultExtension();
		epubWriter.write(book, new FileOutputStream(dirPath + File.separatorChar + bookFileName));

		return Optional.of(loadFileToInputStream(fileName, bookFileName));
	}

	private void preProcess(ByteArrayOutputStream baos, String encoding, List<Section> sectionList,
			Map<String, String> metainfo) throws IOException {
		readStream(baos, encoding, sectionList, metainfo);
	}

	private void createSectionXhtml(Section section, String dirPath, String fileName, int sectionNum) throws
			IOException {
		String sectionFileName = fileName + sectionNum + MediatypeService.XHTML.getDefaultExtension();
		String sectionFilePath = dirPath + File.separatorChar + sectionFileName;

		String head = String.valueOf(
				head(
						title(section.getTitle())
				).renderFormatted()
		);

		String body = String.valueOf(
				body(
						each(section.getParagraphList(), paragraph -> p(paragraph))
				).renderFormatted()
		);

		writeXhtml(sectionFilePath, head, body);
	}

	private void writeXhtml(String sectionFilePath, String head, String body) throws IOException {
		File Sectionfile = new File(sectionFilePath);
		BufferedWriter SectionWriter = new BufferedWriter(new FileWriter(Sectionfile));

		SectionWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		SectionWriter.write("\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\"");
		SectionWriter.write("\r\n\t\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		SectionWriter.write("\r\n");
		SectionWriter.write("\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		SectionWriter.write(head);
		SectionWriter.write(body);
		SectionWriter.write("</html>");

		SectionWriter.close();
	}
}
