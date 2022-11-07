package com.shacomiro.makeabook.api.global.error;

public class ExpiredException extends RuntimeException {
	public ExpiredException(String message) {
		super(message);
	}

	public ExpiredException(String message, Throwable cause) {
		super(message, cause);
	}
}
