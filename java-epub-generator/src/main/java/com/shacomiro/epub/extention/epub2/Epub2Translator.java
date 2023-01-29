package com.shacomiro.epub.extention.epub2;

import static j2html.TagCreator.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.shacomiro.epub.domain.EpubFileInfo;
import com.shacomiro.epub.domain.Section;
import com.shacomiro.epub.exception.FileIOException;
import com.shacomiro.epub.grammar.EpubGrammar;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

public class Epub2Translator {
	private final String epub2ContentsBasePath;
	private final String epub2EbookBasePath;

	public Epub2Translator(String contentsBasePath, String ebookBasePath) {
		this.epub2ContentsBasePath = contentsBasePath + "/epub2";
		this.epub2EbookBasePath = ebookBasePath + "/epub2";
		initEpub2BaseDirectory();
	}

	private static Resource getResource(Path path, String href) {
		Resource resource;

		try (InputStream is = Files.newInputStream(path)) {
			resource = new Resource(is, href);
		} catch (IOException e) {
			throw new FileIOException("Fail to load resource", e);
		}

		return resource;
	}

	public EpubFileInfo createEpub2(String uuid, String fileName, List<String> lines) {
		String fileNameWithoutExtension = FilenameUtils.removeExtension(fileName);
		Path contentsPath = Paths.get(epub2ContentsBasePath, File.separatorChar + uuid);
		try {
			if (!Files.exists(contentsPath)) {
				Files.createDirectory(contentsPath);
			}
		} catch (IOException e) {
			throw new FileIOException(
					"I/O error occurred or fail to create directory '" + contentsPath.toAbsolutePath().normalize() + "'", e);
		}

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

		try (OutputStream os = Files.newOutputStream(bookFilePath)) {
			epubWriter.write(book, os);
		} catch (IOException e) {
			throw new FileIOException("Fail to write epub file", e);
		}

		if (Files.exists(contentsPath)) {
			try (Stream<Path> filesPath = Files.list(contentsPath)) {
				filesPath.forEach(path -> {
					try {
						Files.delete(path);
					} catch (NoSuchFileException e) {
						throw new FileIOException("File '" + path + "' does not exist", e);
					} catch (IOException e) {
						throw new FileIOException("I/O error occurred when deleting file '" + path + "'", e);
					}
				});
				Files.delete(contentsPath);
			} catch (NotDirectoryException e) {
				throw new FileIOException("'" + contentsPath + "' is not a directory", e);
			} catch (IOException e) {
				throw new FileIOException("I/O error occurred when opening the directory '" + contentsPath + "'", e);
			}
		}

		return new EpubFileInfo(bookFileName, bookFilePath);
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

			if (StringUtils.contains(line, EpubGrammar.BOOK_TITLE.getSymbol())) {
				isParagraph = false;
				metainfo.put("Title", StringUtils.split(line, EpubGrammar.BOOK_TITLE.getSymbol())[0]);
			}

			if (StringUtils.contains(line, EpubGrammar.BOOK_AUTHOR.getSymbol())) {
				isParagraph = false;
				metainfo.put("Author", StringUtils.split(line, EpubGrammar.BOOK_AUTHOR.getSymbol())[0]);
			}

			if (StringUtils.contains(line, EpubGrammar.SECTION_TITLE.getSymbol())) {
				isParagraph = false;

				if (isNonSectionEbook) {
					isNonSectionEbook = false;
				} else {
					updateSectionList(sectionList, section, paragraphList);
				}

				paragraphList = new ArrayList<>();
				section = new Section(StringUtils.split(line, EpubGrammar.SECTION_TITLE.getSymbol())[0]);
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
		try (BufferedWriter bw = Files.newBufferedWriter(sectionFilePath)) {
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
			throw new FileIOException("I/O error occurred when writing xhtml file", e);
		}
	}

	private void initEpub2BaseDirectory() {
		Path contentPath = Paths.get(epub2ContentsBasePath);
		Path ebookPath = Paths.get(epub2EbookBasePath);

		try {
			if (!Files.exists(contentPath)) {
				Files.createDirectory(contentPath);
			}
			if (!Files.exists(ebookPath)) {
				Files.createDirectory(ebookPath);
			}
		} catch (IOException e) {
			throw new FileIOException("I/O error occurred or parent directory of epub2 does not exist", e);
		}
	}
}
