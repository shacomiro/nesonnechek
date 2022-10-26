package com.shacomiro.makeabook.api.ebook.error;

public class NoFileException extends RuntimeException {

	public NoFileException(String message) {
		super(message);
	}

	public NoFileException(String message, Throwable cause) {
		super(message, cause);
	}
}
