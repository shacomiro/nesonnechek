package com.shacomiro.aws.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.shacomiro.aws.s3.object.AwsS3ObjectOperator;

public class AwsS3ClientManager {
	private final AwsS3ObjectOperator awsS3ObjectOperator;

	public AwsS3ClientManager(String accessKey, String secretKey, String region) {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
				.withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
		this.awsS3ObjectOperator = new AwsS3ObjectOperator(amazonS3);
	}

	public AwsS3ObjectOperator getAwsS3ObjectOperator() {
		return awsS3ObjectOperator;
	}
}
