package com.shacomiro.epub.grammar;

public enum EpubGrammar {
	BOOK_TITLE("*BT*"),
	BOOK_AUTHOR("*BA*"),
	SECTION_TITLE("*ST*");

	private String symbol;

	private EpubGrammar(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
