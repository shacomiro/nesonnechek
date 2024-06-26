package com.shacomiro.nesonnechek.batch.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shacomiro.aws.s3.AwsS3ClientManager;
import com.shacomiro.aws.s3.exception.AwsS3ObjectHandleException;
import com.shacomiro.nesonnechek.batch.global.config.BatchAwsS3Configuration;
import com.shacomiro.nesonnechek.domain.rds.ebook.entity.Ebook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class DeleteExpiredEbookJobConfiguration {
	private static final int CHUNK_SIZE = 10;
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory emf;
	private final AwsS3ClientManager awsS3ClientManager;
	private final BatchAwsS3Configuration batchAwsS3Configuration;

	@Bean
	public Job deleteExpiredEbookJob() {
		return jobBuilderFactory.get("deleteExpiredEbookJob")
				.start(deleteExpiredEbookStep())
				.build();
	}

	@Bean
	@JobScope
	public Step deleteExpiredEbookStep() {
		return stepBuilderFactory.get("deleteExpiredEbookStep")
				.<Ebook, Ebook>chunk(CHUNK_SIZE)
				.reader(ebookReader())
				.processor(deleteExpiredEbookProcessor())
				.writer(ebookWriter())
				.build();
	}

	@Bean
	@StepScope
	public JpaPagingItemReader<Ebook> ebookReader() {
		return new JpaPagingItemReaderBuilder<Ebook>()
				.name("expiredEbookReader")
				.entityManagerFactory(emf)
				.pageSize(CHUNK_SIZE)
				.queryString("SELECT ef FROM Ebook ef")
				.build();
	}

	@Bean
	@StepScope
	public ItemProcessor<Ebook, Ebook> deleteExpiredEbookProcessor() {
		return ebook -> {
			if (ebook.isExpired() && ebook.isExist()) {
				boolean isDeleted = true;

				try {
					awsS3ClientManager.getAwsS3ObjectHandler()
							.deleteS3Object(batchAwsS3Configuration.getBucketName(), ebook.getUuid());
				} catch (AwsS3ObjectHandleException e) {
					log.error("Fail to delete ebook file");
					isDeleted = false;
				} finally {
					if (isDeleted) {
						ebook.ceaseToExist();
					}
				}
				log.info("Ebook {}({}) is deleted. current statuses are (isExist={}, isExpired={}, isDeleted={})",
						ebook.getName(), ebook.getUuid(), ebook.isExist(), ebook.isExpired(), isDeleted);

				return ebook;
			} else {
				return null;
			}
		};
	}

	@Bean
	@StepScope
	public JpaItemWriter<Ebook> ebookWriter() {
		return new JpaItemWriterBuilder<Ebook>()
				.entityManagerFactory(emf)
				.build();
	}
}
