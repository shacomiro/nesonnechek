package com.shacomiro.nesonnechek.domain.ebook.exception;

import com.shacomiro.nesonnechek.domain.global.exception.NotFoundException;

public class EbookNotFoundException extends NotFoundException {
	public EbookNotFoundException(String message) {
		super(message);
	}

	public EbookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
