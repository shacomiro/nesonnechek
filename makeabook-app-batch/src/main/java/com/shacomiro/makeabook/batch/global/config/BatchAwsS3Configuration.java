package com.shacomiro.makeabook.batch.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.aws.s3.AwsS3ClientManager;

@Configuration
public class BatchAwsS3Configuration {
	private final String bucketName;

	public BatchAwsS3Configuration(@Value("${ext-config.aws.s3.bucket}") String bucketName) {
		this.bucketName = bucketName;
	}

	@Bean
	public AwsS3ClientManager awsS3ClientManager(
			@Value("${ext-config.aws.s3.access-key}") String accessKey,
			@Value("${ext-config.aws.s3.secret-key}") String secretKey,
			@Value("${ext-config.aws.s3.region}") String region
	) {
		return new AwsS3ClientManager(accessKey, secretKey, region);
	}

	public String getBucketName() {
		return bucketName;
	}
}
