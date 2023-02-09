package com.shacomiro.aws.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.shacomiro.aws.s3.handler.AwsS3ObjectHandler;

public class AwsS3ClientManager {
	private final AwsS3ObjectHandler awsS3ObjectHandler;

	public AwsS3ClientManager(String accessKey, String secretKey, String region) {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
				.withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
		this.awsS3ObjectHandler = new AwsS3ObjectHandler(amazonS3);
	}

	public AwsS3ObjectHandler getAwsS3ObjectHandler() {
		return awsS3ObjectHandler;
	}
}
