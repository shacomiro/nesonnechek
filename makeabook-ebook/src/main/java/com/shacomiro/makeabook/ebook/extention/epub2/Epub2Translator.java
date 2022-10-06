package com.shacomiro.makeabook.ebook.extention.epub2;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shacomiro.makeabook.ebook.extention.epub2.domain.Section;

import nl.siegmann.epublib.domain.Book;

public class Epub2Translator {

	public Optional<InputStream> createEpub2(ByteArrayOutputStream baos, String encoding, String fileName) {
		Book book = new Book();
		List<Section> sectionList = new ArrayList<>();

		readStream(baos, encoding, book, sectionList);

		for (Section section : sectionList) {

		}

		return null;
	}
}
