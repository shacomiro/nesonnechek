package com.shacomiro.nesonnechek.batch.job;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.shacomiro.aws.s3.AwsS3ClientManager;
import com.shacomiro.nesonnechek.batch.config.TestBatchConfig;
import com.shacomiro.nesonnechek.batch.global.config.BatchAwsS3Configuration;
import com.shacomiro.nesonnechek.batch.global.config.BatchS3MockConfiguration;
import com.shacomiro.nesonnechek.domain.rds.ebook.repository.EbookRdsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {DeleteExpiredEbookJobConfiguration.class, TestBatchConfig.class, BatchAwsS3Configuration.class,
		BatchS3MockConfiguration.class})
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
@ActiveProfiles(value = {"batch-app-test"})
public class DeleteExpiredEbookJobConfigurationTest {
	private static final String EMPTY_EBOOK_FILE_PATH = "./src/test/resources/test_ebook.epub";
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private EbookRdsRepository ebookRdsRepository;
	@Autowired
	private AwsS3ClientManager awsS3ClientManager;
	@Value("${aws.s3.bucket}")
	private String testBucketName;

	@Before
	public void setUp() throws Exception {
		ebookRdsRepository.findAll().forEach(ebook -> {
			if (ebook.isExist()) {
				awsS3ClientManager.getAwsS3ObjectHandler().uploadS3Object(testBucketName, ebook.getUuid(), EMPTY_EBOOK_FILE_PATH);
			}
		});
	}

	@Test
	@Order(1)
	@DisplayName("만료된 전자책 파일 삭제 성공")
	public void deleteExpiredEbookJobSuccess() throws Exception {
		//given
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("date", System.currentTimeMillis())
				.toJobParameters();

		//when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		//then
		Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
		Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
	}
}
