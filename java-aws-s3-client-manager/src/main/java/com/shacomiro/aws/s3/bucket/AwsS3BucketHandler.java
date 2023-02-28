package com.shacomiro.aws.s3.bucket;

import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;
import com.shacomiro.aws.s3.exception.AwsS3BucketHandleException;

public class AwsS3BucketHandler {
	private final AmazonS3 amazonS3;

	public AwsS3BucketHandler(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	public Bucket getBucket(String bucketName) {
		Bucket bucket = null;
		List<Bucket> buckets = getBuckets();

		for (Bucket b : buckets) {
			if (b.getName().equals(bucketName)) {
				bucket = b;
			}
		}

		return bucket;
	}

	public List<Bucket> getBuckets() {
		return amazonS3.listBuckets();
	}

	public Bucket createBucket(String bucketName) {
		if (amazonS3.doesBucketExistV2(bucketName)) {
			return getBucket(bucketName);
		}

		try {
			return amazonS3.createBucket(bucketName);
		} catch (AmazonServiceException e) {
			throw new AwsS3BucketHandleException("Fail to create bucket '" + bucketName + "'");
		}
	}

	public void deleteBucket(String bucketName) {
		try {
			ObjectListing objectListing = amazonS3.listObjects(bucketName);
			while (true) {
				for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
					amazonS3.deleteObject(bucketName, os.getKey());
				}

				if (objectListing.isTruncated()) {
					objectListing = amazonS3.listNextBatchOfObjects(objectListing);
				} else {
					break;
				}
			}

			VersionListing versionListing = amazonS3.listVersions(new ListVersionsRequest().withBucketName(bucketName));
			while (true) {
				for (S3VersionSummary vs : versionListing.getVersionSummaries()) {
					amazonS3.deleteVersion(bucketName, vs.getKey(), vs.getVersionId());
				}

				if (versionListing.isTruncated()) {
					versionListing = amazonS3.listNextBatchOfVersions(versionListing);
				} else {
					break;
				}
			}

			amazonS3.deleteBucket(bucketName);
		} catch (AmazonServiceException e) {
			throw new AwsS3BucketHandleException("Fail to delete bucket '" + bucketName + "'");
		}
	}
}
