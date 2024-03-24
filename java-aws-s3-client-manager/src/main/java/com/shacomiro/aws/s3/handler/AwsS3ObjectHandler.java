package com.shacomiro.aws.s3.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.shacomiro.aws.s3.exception.AwsS3ObjectHandleException;
import com.shacomiro.aws.s3.exception.AwsS3ObjectIOException;

public class AwsS3ObjectHandler {
	private final AmazonS3 amazonS3;

	public AwsS3ObjectHandler(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public void uploadS3Object(String bucketName, String keyName, String filePath) {
		try {
			amazonS3.putObject(bucketName, keyName, new File(filePath));
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectHandleException("The specified bucket and object key not exist");
		}
	}

	public void uploadS3Object(String bucketName, String keyName, String filePath, ObjectMetadata objectMetadata) {
		try (InputStream fis = new FileInputStream(filePath)) {
			amazonS3.putObject(bucketName, keyName, fis, objectMetadata);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectHandleException("The specified bucket and object key not exist");
		} catch (FileNotFoundException e) {
			throw new AwsS3ObjectHandleException("File not found");
		} catch (IOException e) {
			throw new AwsS3ObjectHandleException("I/O error occurred while load input stream");
		}
	}

	public ListObjectsV2Result getListS3ObjectsV2Result(String bucketName) {
		return amazonS3.listObjectsV2(bucketName);
	}

	public List<String> getS3ObjectList(String bucketName) {
		return getListS3ObjectsV2Result(bucketName).getObjectSummaries()
				.stream()
				.map(S3ObjectSummary::getKey)
				.collect(Collectors.toList());
	}

	public S3Object downloadS3Object(String bucketName, String objectKey) {
		try {
			return amazonS3.getObject(bucketName, objectKey);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectHandleException("The specified bucket and object key not exist");
		}
	}

	public byte[] downloadS3ObjectBytes(String bucketName, String objectKey) {
		try (
				S3Object s3Object = downloadS3Object(bucketName, objectKey);
				S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
		) {
			return s3ObjectInputStream.readAllBytes();
		} catch (IOException e) {
			throw new AwsS3ObjectIOException("I/O error occurred while read bytes from S3ObjectInputStream");
		}
	}

	public void copyS3Object(String fromBucket, String fromObjectKey, String toBucket, String toObjectKey) {
		try {
			amazonS3.copyObject(fromBucket, fromObjectKey, toBucket, toObjectKey);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectHandleException("The specified buckets or object keys not exist");
		}
	}

	public void deleteS3Object(String bucketName, String objectKey) {
		try {
			amazonS3.deleteObject(bucketName, objectKey);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectHandleException("The specified bucket and object key not exist");
		}
	}

	public void deleteS3Objects(String bucketName, String[] objectKeys) {
		try {
			DeleteObjectsRequest deleteObjectRequest = new DeleteObjectsRequest(bucketName)
					.withKeys(objectKeys);
			amazonS3.deleteObjects(deleteObjectRequest);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectHandleException("The specified bucket or object keys not exist");
		}
	}
}
