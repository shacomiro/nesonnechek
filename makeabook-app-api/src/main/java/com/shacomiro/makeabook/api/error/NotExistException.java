package com.shacomiro.makeabook.api.error;

public class NotExistException extends RuntimeException {

	public NotExistException(String message) {
		super(message);
	}

	public NotExistException(String message, Throwable cause) {
		super(message, cause);
	}
}
