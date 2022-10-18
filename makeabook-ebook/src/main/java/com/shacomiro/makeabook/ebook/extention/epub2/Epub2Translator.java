package com.shacomiro.makeabook.ebook.extention.epub2;

import static com.shacomiro.makeabook.ebook.util.IOUtil.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.shacomiro.makeabook.ebook.extention.epub2.domain.Section;

import nl.siegmann.epublib.domain.Book;

public class Epub2Translator {

	public Optional<InputStream> createEpub2(ByteArrayOutputStream baos, String encoding, String fileName) throws
			IOException {
		createDirectoryIfNotExists(fileName);
		String dirPath = getDirPath(fileName).toString();

		List<Section> sectionList = new ArrayList<>();
		Map<String, String> metainfo = new HashMap<>();
		preProcess(baos, encoding, sectionList, metainfo);

		for (Section section : sectionList) {

		}

		Book book = new Book();

		return null;
	}

	private void preProcess(ByteArrayOutputStream baos, String encoding, List<Section> sectionList,
			Map<String, String> metainfo) throws IOException {
		readStream(baos, encoding, sectionList, metainfo);
	}
}
