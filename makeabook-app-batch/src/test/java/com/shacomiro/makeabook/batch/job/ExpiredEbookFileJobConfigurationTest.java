package com.shacomiro.makeabook.batch.job;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.After;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shacomiro.makeabook.batch.config.TestBatchConfig;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;
import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFileExtension;
import com.shacomiro.makeabook.domain.rds.ebookfile.repository.EbookFileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {ExpiredEbookFileJobConfiguration.class, TestBatchConfig.class})
public class ExpiredEbookFileJobConfigurationTest {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private EbookFileRepository ebookFileRepository;

	@After
	public void tearDown() {
		ebookFileRepository.deleteAllInBatch();
	}

	@Test
	@Order(1)
	@DisplayName("만료된 전자책 파일 삭제 성공")
	public void deleteExpiredEbookFileJobSuccess() throws Exception {
		//given
		String uuid = UUID.randomUUID().toString();
		LocalDateTime localDateTime = LocalDateTime.of(2022, 8, 13, 18, 30, 0);
		ebookFileRepository.save(EbookFile.byAllArguments()
				.seq(null)
				.uuid(uuid)
				.filename("file1")
				.fileType(EbookFileExtension.EPUB2.getEbookExt().toLowerCase())
				.fileExtension("epub")
				.downloadUrl("http://localhost:8080/api/ebook/download/" + uuid)
				.downloadCount(0)
				.createdAt(localDateTime)
				.expiredAt(localDateTime.plusDays(14))
				.user(null)
				.build());

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