package com.shacomiro.aws.s3.object;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.shacomiro.aws.s3.exception.AwsS3ObjectOperateException;

import io.findify.s3mock.S3Mock;

class AwsS3ObjectOperatorTest {
	private static final Logger LOG = LoggerFactory.getLogger(AwsS3ObjectOperatorTest.class);
	private static final S3Mock S3_Mock = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
	private static final AmazonS3 AMAZON_S3 = AmazonS3ClientBuilder.standard()
			.withPathStyleAccessEnabled(true)
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
					"http://127.0.0.1:8001",
					Regions.AP_NORTHEAST_2.getName())
			)
			.withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
			.build();
	private static final String BUCKET_NAME = "mock-bucket";
	private final AwsS3ObjectOperator awsS3ObjectOperator;

	public AwsS3ObjectOperatorTest() {
		this.awsS3ObjectOperator = new AwsS3ObjectOperator(AMAZON_S3);
	}

	@BeforeAll
	static void beforeAll() {
		S3_Mock.start();
	}

	@AfterAll
	static void afterAll() {
		AMAZON_S3.shutdown();
		S3_Mock.stop();
	}

	@BeforeEach
	void setUp() {
		AMAZON_S3.createBucket(BUCKET_NAME);
	}

	@AfterEach
	void tearDown() {
		AMAZON_S3.deleteBucket(BUCKET_NAME);
	}

	@Test
	@Order(1)
	@DisplayName("AWS S3 오브젝트 업로드/다운로드")
	void AwsS3UploadAndDownload() throws IOException {
		//given
		String keyName = "mock-file-key";
		String path = "./src/test/resources/mock-file.txt";
		String contentType = "text/plain";
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(Files.size(Paths.get(path)));

		//when
		awsS3ObjectOperator.uploadS3Object(BUCKET_NAME, keyName, path, objectMetadata);
		S3Object s3Object = awsS3ObjectOperator.downloadS3Object(BUCKET_NAME, keyName);

		//then
		Assertions.assertEquals(contentType, s3Object.getObjectMetadata().getContentType());
		Assertions.assertEquals(new String(Files.readString(Paths.get(path))),
				new String(s3Object.getObjectContent().readAllBytes()));
	}

	@Test
	@Order(2)
	@DisplayName("AWS S3 오브젝트 목록 조회")
	void AwsS3ObjectList() throws IOException {
		//given
		List<String> keyNameList = List.of("mock-file-key-1", "mock-file-key-2", "mock-file-key-3");
		String path = "./src/test/resources/mock-file.txt";
		String contentType = "text/plain";
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(Files.size(Paths.get(path)));
		for (String keyName : keyNameList) {
			awsS3ObjectOperator.uploadS3Object(BUCKET_NAME, keyName, path, objectMetadata);
		}

		//when
		List<String> S3ObjectList = awsS3ObjectOperator.getS3ObjectList(BUCKET_NAME);

		//then
		Assertions.assertEquals(keyNameList.size(), S3ObjectList.size());
		Assertions.assertIterableEquals(keyNameList, S3ObjectList);
	}

	@Test
	@Order(3)
	@DisplayName("AWS S3 오브젝트 복사")
	void AwsS3ObjectCopy() throws IOException {
		//given
		String keyName = "mock-file-key";
		String copyKeyName = "copy-mock-file-key";
		String path = "./src/test/resources/mock-file.txt";
		String contentType = "text/plain";
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(Files.size(Paths.get(path)));
		awsS3ObjectOperator.uploadS3Object(BUCKET_NAME, keyName, path, objectMetadata);

		//when
		awsS3ObjectOperator.copyS3Object(BUCKET_NAME, keyName, BUCKET_NAME, copyKeyName);
		S3Object copyS3Object = awsS3ObjectOperator.downloadS3Object(BUCKET_NAME, copyKeyName);

		//then
		Assertions.assertEquals(contentType, copyS3Object.getObjectMetadata().getContentType());
		Assertions.assertEquals(new String(Files.readString(Paths.get(path))),
				new String(copyS3Object.getObjectContent().readAllBytes()));
	}

	@Test
	@Order(4)
	@DisplayName("AWS S3 오브젝트 삭제")
	void AwsS3ObjectDelete() throws IOException {
		//given
		String keyName = "mock-file-key";
		String path = "./src/test/resources/mock-file.txt";
		String contentType = "text/plain";
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(Files.size(Paths.get(path)));
		awsS3ObjectOperator.uploadS3Object(BUCKET_NAME, keyName, path, objectMetadata);

		//when
		awsS3ObjectOperator.deleteS3Object(BUCKET_NAME, keyName);

		//then
		Assertions.assertFalse(AMAZON_S3.doesObjectExist(BUCKET_NAME, keyName));
	}

	@Test
	@Order(5)
	@DisplayName("존재하지 않는 AWS S3 오브젝트 다운로드")
	void AwsS3NonExistObjectDownload() {
		//given
		String keyName = "non-exist-mock-file-key";

		//when, then
		Assertions.assertThrows(AwsS3ObjectOperateException.class,
				() -> awsS3ObjectOperator.downloadS3Object(BUCKET_NAME, keyName));
	}
}
