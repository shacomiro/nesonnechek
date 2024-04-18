package com.shacomiro.nesonnechek.batch.scheduler;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shacomiro.nesonnechek.batch.job.DeleteExpiredEbookJobConfiguration;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BatchScheduler {
	private final JobLauncher jobLauncher;
	private final DeleteExpiredEbookJobConfiguration deleteExpiredEbookJobConfiguration;

	@Scheduled(cron = "0 0 0 * * *")
	public void runDeleteExpiredEbookJob() throws
			JobInstanceAlreadyCompleteException,
			JobExecutionAlreadyRunningException,
			JobParametersInvalidException,
			JobRestartException {
		jobLauncher.run(deleteExpiredEbookJobConfiguration.deleteExpiredEbookJob(),
				new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
	}
}
