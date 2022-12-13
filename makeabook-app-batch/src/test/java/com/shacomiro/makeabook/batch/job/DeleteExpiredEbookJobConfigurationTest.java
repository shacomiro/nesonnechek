package com.shacomiro.makeabook.batch.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;
import com.shacomiro.makeabook.domain.rds.ebook.entity.EbookType;
import com.shacomiro.makeabook.domain.rds.ebook.repository.EbookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {DeleteExpiredEbookJobConfiguration.class, TestBatchConfig.class})
public class DeleteExpiredEbookJobConfigurationTest {
	private static final List<Ebook> TEST_EBOOKS = new ArrayList<>() {{
		for (int i = 1; i <= 3; i++) {
			String uuid = UUID.randomUUID().toString();
			add(new Ebook(null,
					uuid,
					"test_file" + i,
					EbookType.EPUB2,
					0,
					LocalDateTime.now().minusDays(i * 4),
					LocalDateTime.now().minusDays(i * 4).plusDays(7),
					true,
					null)
			);
		}
	}};
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;
	@Autowired
	private EbookRepository ebookRepository;

	@After
	public void tearDown() {
		ebookRepository.deleteAllInBatch(TEST_EBOOKS);
	}

	@Test
	@Order(1)
	@DisplayName("만료된 전자책 파일 삭제 성공")
	public void deleteExpiredEbookJobSuccess() throws Exception {
		//given
		ebookRepository.saveAll(TEST_EBOOKS);

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
