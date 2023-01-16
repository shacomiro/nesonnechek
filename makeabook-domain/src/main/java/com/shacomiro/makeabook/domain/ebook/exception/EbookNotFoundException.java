package com.shacomiro.makeabook.domain.ebook.exception;

public class EbookNotFoundException extends RuntimeException {
	public EbookNotFoundException(String message) {
		super(message);
	}

	public EbookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
