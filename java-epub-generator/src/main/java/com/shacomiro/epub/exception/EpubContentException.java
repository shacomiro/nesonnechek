package com.shacomiro.epub.exception;

public class EpubContentException extends RuntimeException {

	public EpubContentException(String message) {
		super(message);
	}

	public EpubContentException(String message, Throwable cause) {
		super(message, cause);
	}
}
