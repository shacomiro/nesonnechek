package com.shacomiro.aws.s3.exception;

public class AwsS3ObjectHandleException extends RuntimeException {

	public AwsS3ObjectHandleException(String message) {
		super(message);
	}

	public AwsS3ObjectHandleException(String message, Throwable cause) {
		super(message, cause);
	}
}
