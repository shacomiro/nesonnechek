package com.shacomiro.makeabook.batch.global.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.shacomiro.aws.s3.AwsS3ClientManager;
import com.shacomiro.aws.s3.exception.AwsS3BucketHandleException;

import io.findify.s3mock.S3Mock;

@Configuration
@Profile(value = {"batch-app-local", "batch-app-test"})
public class BatchS3MockConfiguration {
	private final S3Mock s3Mock;
	private final AwsS3ClientManager awsS3ClientManager;
	private final String localBucketName;

	public BatchS3MockConfiguration(@Value("${aws.s3.port}") int s3MockPort, @Value("${aws.s3.bucket}") String localBucketName,
			AwsS3ClientManager awsS3ClientManager) {
		this.s3Mock = new S3Mock.Builder()
				.withPort(s3MockPort)
				.withInMemoryBackend()
				.build();
		this.awsS3ClientManager = awsS3ClientManager;
		this.localBucketName = localBucketName;
	}

	@PostConstruct
	public void startS3Mock() throws AwsS3BucketHandleException {
		s3Mock.start();
		awsS3ClientManager.getAwsS3BucketHandler().createBucket(localBucketName);
	}

	@PreDestroy
	public void stopS3Mock() {
		awsS3ClientManager.getAwsS3BucketHandler().deleteBucket(localBucketName);
		awsS3ClientManager.shutdown();
		s3Mock.stop();
	}
}
