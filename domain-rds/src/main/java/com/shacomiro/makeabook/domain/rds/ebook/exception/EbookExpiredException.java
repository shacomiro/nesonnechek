package com.shacomiro.makeabook.domain.rds.ebook.exception;

public class EbookExpiredException extends RuntimeException {
	public EbookExpiredException(String message) {
		super(message);
	}

	public EbookExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
