package com.shacomiro.makeabook.ebook.extention.epub2;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;
import static j2html.TagCreator.*;

import java.io.BufferedWriter;
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
import org.apache.commons.lang3.StringUtils;

import com.shacomiro.makeabook.ebook.domain.EpubFileInfo;
import com.shacomiro.makeabook.ebook.domain.Section;
import com.shacomiro.makeabook.ebook.error.FileIOException;
import com.shacomiro.makeabook.ebook.grammar.EbookGrammar;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

public class Epub2Translator {
	private final String epub2ContentsBasePath;
	private final String epub2EbookBasePath;

	private static Resource getResource(Path path, String href) {
		Resource resource;

		try (InputStream is = loadFileToInputStream(path)) {
			resource = new Resource(is, href);
		} catch (IOException e) {
			throw new FileIOException("fail to load resource", e);
		}

		return resource;
	}

	public Epub2Translator(String contentsBasePath, String ebookBasePath) {
		this.epub2ContentsBasePath = contentsBasePath + "/epub2";
		this.epub2EbookBasePath = ebookBasePath + "/epub2";
		initEpub2BaseDirectory();
	}

	public EpubFileInfo createEpub2(String uuid, String fileName, List<String> lines) {
		String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		Path contentsPath = Paths.get(epub2ContentsBasePath, File.separatorChar + uuid);
		createDirectory(contentsPath);

		List<Section> sectionList = new ArrayList<>();
		Map<String, String> metainfo = new HashMap<>();
		convertLinesToEbookInfo(lines, sectionList, metainfo);

		int sectionNum = 0;
		for (Section section : sectionList) {
			sectionNum++;
			createSectionXhtml(section, contentsPath, sectionNum);
		}

		Book book = new Book();
		Metadata metadata = book.getMetadata();

		metadata.addTitle(metainfo.getOrDefault("Title", fileNameWithoutExtension));
		if (metainfo.containsKey("Author")) {
			metadata.addAuthor(new Author(metainfo.get("Author")));
		}

		File[] contentsFiles = contentsPath.toFile().listFiles();
		for (int i = 0; i < Objects.requireNonNull(contentsFiles).length; i++) {
			book.addSection(sectionList.get(i).getTitle(),
					getResource(Path.of(contentsFiles[i].getPath()), contentsFiles[i].getName()));
		}

		EpubWriter epubWriter = new EpubWriter();
		String bookFileName = metainfo.getOrDefault("Title", fileNameWithoutExtension);
		Path bookFilePath = Paths.get(epub2EbookBasePath,
						File.separatorChar + uuid + MediatypeService.EPUB.getDefaultExtension())
				.toAbsolutePath()
				.normalize();

		try (OutputStream os = new FileOutputStream(bookFilePath.toString())) {
			epubWriter.write(book, os);
		} catch (IOException e) {
			throw new FileIOException("Fail to write epub file", e);
		} finally {
			deleteDirectory(contentsPath);
		}

		return EpubFileInfo.builder()
				.fileName(bookFileName)
				.filePath(bookFilePath)
				.build();
	}

	private void convertLinesToEbookInfo(List<String> lines, List<Section> sectionList, Map<String, String> metainfo) {
		Section section = new Section();
		List<String> paragraphList = new ArrayList<>();
		boolean isNonSectionEbook = true;

		for (String line : lines) {
			boolean isParagraph = true;

			if (StringUtils.isEmpty(line)) {
				continue;
			}

			if (StringUtils.contains(line, EbookGrammar.BOOK_TITLE)) {
				isParagraph = false;
				metainfo.put("Title", StringUtils.split(line, EbookGrammar.BOOK_TITLE)[0]);
			}

			if (StringUtils.contains(line, EbookGrammar.BOOK_AUTHOR)) {
				isParagraph = false;
				metainfo.put("Author", StringUtils.split(line, EbookGrammar.BOOK_AUTHOR)[0]);
			}

			if (StringUtils.contains(line, EbookGrammar.SECTION_TITLE)) {
				isParagraph = false;

				if (isNonSectionEbook) {
					isNonSectionEbook = false;
				} else {
					updateSectionList(sectionList, section, paragraphList);
				}

				paragraphList = new ArrayList<>();
				section = Section.builder()
						.title(StringUtils.split(line, EbookGrammar.SECTION_TITLE)[0])
						.build();
			}

			if (isParagraph) {
				paragraphList.add(line);
			}
		}

		updateSectionList(sectionList, section, paragraphList);
	}

	private void updateSectionList(List<Section> sectionList, Section section, List<String> paragraphList) {
		section.updateParagraphList(paragraphList);
		sectionList.add(section);
	}

	private void createSectionXhtml(Section section, Path path, int sectionNum) {
		String sectionFileName = "chapter" + sectionNum + MediatypeService.XHTML.getDefaultExtension();
		Path sectionFilePath = Paths.get(path.toString(), File.separatorChar + sectionFileName);

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

	private void writeXhtml(Path sectionFilePath, String head, String body) {
		File sectionFile = sectionFilePath.toFile();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(sectionFile))) {
			bw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			bw.write("\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\"");
			bw.write("\r\n\t\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			bw.write("\r\n");
			bw.write("\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			bw.write("\r\n");
			bw.write(head);
			bw.write(body);
			bw.write("</html>");
			bw.flush();
		} catch (IOException e) {
			throw new FileIOException("Fail to write xhtml file", e);
		}
	}

	private void initEpub2BaseDirectory() {
		createDirectory(Paths.get(epub2ContentsBasePath));
		createDirectory(Paths.get(epub2EbookBasePath));
	}
}
