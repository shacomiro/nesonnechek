package com.shacomiro.makeabook.batch.job;

import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shacomiro.makeabook.batch.config.TestBatchConfig;
import com.shacomiro.makeabook.domain.rds.ebookfile.repository.EbookFileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {ExpiredEbookFileJobConfiguration.class, TestBatchConfig.class})
public class ExpiredEbookFileJobConfigurationTest {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	private EbookFileRepository ebookFileRepository;

	@After
	public void tearDown() {
		ebookFileRepository.deleteAllInBatch();
	}

	@Test
	@Order(1)
	@DisplayName("만료된 전자책 파일 삭제 성공")
	public void deleteExpiredEbookFileJobSuccess() {
		//given
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("date", System.currentTimeMillis())
				.toJobParameters();

		//when

		//then

	}
}