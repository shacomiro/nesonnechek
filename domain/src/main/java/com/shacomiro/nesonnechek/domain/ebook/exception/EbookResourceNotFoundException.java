package com.shacomiro.nesonnechek.domain.ebook.exception;

import com.shacomiro.nesonnechek.domain.global.exception.NotFoundException;

public class EbookResourceNotFoundException extends NotFoundException {

	public EbookResourceNotFoundException(String message) {
		super(message);
	}

	public EbookResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
