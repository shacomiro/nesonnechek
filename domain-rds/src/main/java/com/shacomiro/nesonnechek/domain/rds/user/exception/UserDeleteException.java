package com.shacomiro.nesonnechek.domain.rds.user.exception;

public class UserDeleteException extends RuntimeException {
	public UserDeleteException(String message) {
		super(message);
	}

	public UserDeleteException(String message, Throwable cause) {
		super(message, cause);
	}
}
