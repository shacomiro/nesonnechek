package com.shacomiro.makeabook.ebook.grammar;

public class EbookGrammar {
	public static final String BOOK_TITLE = "*BT*";
	public static final String BOOK_AUTHOR = "*BA*";
	public static final String SECTION_TITLE = "*ST*";

	private EbookGrammar() {
		throw new IllegalStateException("Constant class");
	}
}
