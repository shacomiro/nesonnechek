package com.shacomiro.aws.s3.exception;

public class AwsS3ObjectOperateException extends RuntimeException {

	public AwsS3ObjectOperateException(String message) {
		super(message);
	}

	public AwsS3ObjectOperateException(String message, Throwable cause) {
		super(message, cause);
	}
}
