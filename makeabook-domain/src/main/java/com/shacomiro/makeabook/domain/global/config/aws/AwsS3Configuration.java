package com.shacomiro.makeabook.domain.global.config.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.aws.s3.AwsS3ClientManager;

@Configuration
public class AwsS3Configuration {
	private final String bucketName;

	public AwsS3Configuration(@Value("${aws.s3.bucket}") String bucketName) {
		this.bucketName = bucketName;
	}

	@Bean
	public AwsS3ClientManager awsS3ClientManager(
			@Value("${aws.s3.key.access}") String accessKey,
			@Value("${aws.s3.key.secret}") String secretKey,
			@Value("${aws.s3.region}") String region
	) {
		return new AwsS3ClientManager(accessKey, secretKey, region);
	}

	public String getBucketName() {
		return bucketName;
	}
}
