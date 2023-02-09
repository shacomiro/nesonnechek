package com.shacomiro.epub.exception;

public class EpubException extends RuntimeException {

	public EpubException(String message) {
		super(message);
	}

	public EpubException(String message, Throwable cause) {
		super(message, cause);
	}
}
