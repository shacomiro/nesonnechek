package com.shacomiro.aws.s3.exception;

public class AwsS3BucketHandleException extends RuntimeException {
	
	public AwsS3BucketHandleException(String message) {
		super(message);
	}

	public AwsS3BucketHandleException(String message, Throwable cause) {
		super(message, cause);
	}
}
