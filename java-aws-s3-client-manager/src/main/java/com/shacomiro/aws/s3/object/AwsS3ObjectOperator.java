package com.shacomiro.aws.s3.object;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.shacomiro.aws.s3.exception.AwsS3ObjectIOException;
import com.shacomiro.aws.s3.exception.AwsS3ObjectOperateException;

public class AwsS3ObjectOperator {
	private final AmazonS3 amazonS3;

	public AwsS3ObjectOperator(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public void uploadS3Object(String bucketName, String keyName, String filePath) {
		try {
			amazonS3.putObject(bucketName, keyName, new File(filePath));
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectOperateException("The specified bucket and object key not exist");
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
		return amazonS3.getObject(bucketName, objectKey);
	}

	public byte[] getS3ObjectBytes(String bucketName, String objectKey) {
		try (
				S3Object s3Object = downloadS3Object(bucketName, objectKey);
				S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
		) {
			return s3ObjectInputStream.readAllBytes();
		} catch (IOException e) {
			throw new AwsS3ObjectIOException("I/O error occurs while read bytes from S3ObjectInputStream");
		}
	}

	public void copyS3Object(String fromBucket, String fromObjectKey, String toBucket, String toObjectKey) {
		try {
			amazonS3.copyObject(fromBucket, fromObjectKey, toBucket, toObjectKey);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectOperateException("The specified buckets or object keys not exist");
		}
	}

	public void deleteS3Object(String bucketName, String objectKey) {
		try {
			amazonS3.deleteObject(bucketName, objectKey);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectOperateException("The specified bucket and object key not exist");
		}
	}

	public void deleteS3Objects(String bucketName, String[] objectKeys) {
		try {
			DeleteObjectsRequest deleteObjectRequest = new DeleteObjectsRequest(bucketName)
					.withKeys(objectKeys);
			amazonS3.deleteObjects(deleteObjectRequest);
		} catch (AmazonServiceException e) {
			throw new AwsS3ObjectOperateException("The specified bucket or object keys not exist");
		}
	}
}
