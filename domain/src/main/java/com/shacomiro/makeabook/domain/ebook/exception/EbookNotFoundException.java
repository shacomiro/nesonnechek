package com.shacomiro.makeabook.domain.ebook.exception;

import com.shacomiro.makeabook.domain.global.exception.NotFoundException;

public class EbookNotFoundException extends NotFoundException {
	public EbookNotFoundException(String message) {
		super(message);
	}

	public EbookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
