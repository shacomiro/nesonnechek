package com.shacomiro.aws.s3.exception;

public class AwsS3ObjectIOException extends AwsS3ObjectHandleException {

	public AwsS3ObjectIOException(String message) {
		super(message);
	}

	public AwsS3ObjectIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
