package com.shacomiro.makeabook.ebook.extention.epub2;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;
import static j2html.TagCreator.*;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;

import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.domain.Section;

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
		InputStream is = getResource(path);
		Resource resource = new Resource(is, href);
		is.close();

		return resource;
	}

	private static Resource getResource(String dirName, String fileName, String href) throws IOException {
		InputStream is = loadFileToInputStream(dirName, fileName);
		Resource resource = new Resource(is, href);
		is.close();

		return resource;
	}

	public EpubFileInfo createEpub2(byte[] bytes, String encoding, String fileName) throws
			IOException {
		String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		createDirectory(fileNameWithoutExtension);
		String dirPath = getDirPath(fileNameWithoutExtension).toString();

		List<Section> sectionList = new ArrayList<>();
		Map<String, String> metainfo = new HashMap<>();
		convertBytesTextToEbookInfo(bytes, encoding, sectionList, metainfo);

		int sectionNum = 0;
		for (Section section : sectionList) {
			sectionNum++;
			createSectionXhtml(section, dirPath, sectionNum);
		}

		Book book = new Book();
		Metadata metadata = book.getMetadata();

		metadata.addTitle(metainfo.get("Title"));
		metadata.addAuthor(new Author(metainfo.get("Author")));

		String[] xhtmlFileNames = new File(dirPath).list();
		for (int i = 0; i < Objects.requireNonNull(xhtmlFileNames).length; i++) {
			book.addSection(sectionList.get(i).getTitle(),
					getResource(fileNameWithoutExtension, xhtmlFileNames[i], xhtmlFileNames[i]));
		}

		EpubWriter epubWriter = new EpubWriter();
		String bookFileName = metainfo.get("Title") + MediatypeService.EPUB.getDefaultExtension();
		Path bookFilePath = Paths.get(dirPath, File.separatorChar + bookFileName).toAbsolutePath().normalize();
		OutputStream os = new FileOutputStream(bookFilePath.toString());
		epubWriter.write(book, os);
		os.close();

		return EpubFileInfo.builder()
				.fileName(bookFileName)
				.filePath(bookFilePath)
				.build();
	}

	private void convertBytesTextToEbookInfo(byte[] bytes, String encoding, List<Section> sectionList,
			Map<String, String> metainfo) throws IOException {
		InputStream is = new ByteArrayInputStream(bytes);
		readStream(is, encoding, sectionList, metainfo);
		is.close();
	}

	private void createSectionXhtml(Section section, String dirPath, int sectionNum) throws
			IOException {
		String sectionFileName = "chapter" + sectionNum + MediatypeService.XHTML.getDefaultExtension();
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
		File sectionFile = new File(sectionFilePath);

		FileWriter fw = new FileWriter(sectionFile);
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		bw.write("\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\"");
		bw.write("\r\n\t\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		bw.write("\r\n");
		bw.write("\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		bw.write("\r\n");
		bw.write(head);
		bw.write(body);
		bw.write("</html>");

		bw.close();
		fw.close();
	}
}
