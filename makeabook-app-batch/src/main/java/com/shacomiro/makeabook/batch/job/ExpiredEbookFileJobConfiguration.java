package com.shacomiro.makeabook.batch.job;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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

import com.shacomiro.makeabook.domain.rds.ebookfile.entity.EbookFile;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ExpiredEbookFileJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory emf;
	private static final int CHUNK_SIZE = 10;

	@Bean
	public Job expiredEbookFileDeleteJob() {
		return jobBuilderFactory.get("expiredEbookFileDeleteJob")
				.start(expiredEbookFileDeleteStep())
				.build();
	}

	@Bean
	@JobScope
	public Step expiredEbookFileDeleteStep() {
		return stepBuilderFactory.get("expiredEbookFileDeleteStep")
				.<EbookFile, EbookFile>chunk(CHUNK_SIZE)
				.reader(ebookFileReader())
				.processor(deleteExpiredEbookFileProcessor())
				.writer(ebookFilewriter())
				.build();
	}

	@Bean
	@StepScope
	public JpaPagingItemReader<EbookFile> ebookFileReader() {
		return new JpaPagingItemReaderBuilder<EbookFile>()
				.name("expiredEbookFileReader")
				.entityManagerFactory(emf)
				.pageSize(CHUNK_SIZE)
				.queryString("SELECT ef FROM EbookFile ef")
				.build();
	}

	@Bean
	@StepScope
	public ItemProcessor<EbookFile, EbookFile> deleteExpiredEbookFileProcessor() {
		return ebookFile -> {

			if (ebookFile.isExpired()) {
				Path targetPath = Paths.get("./files/ebook/", ebookFile.getFileType() + "/",
						ebookFile.getUuid() + "." + ebookFile.getFileExtension()).normalize().toAbsolutePath();
				boolean deleteResult = Files.deleteIfExists(targetPath);

				return ebookFile;
			} else {
				return null;
			}
		};
	}

	@Bean
	@StepScope
	public JpaItemWriter<EbookFile> ebookFilewriter() {
		return new JpaItemWriterBuilder<EbookFile>()
				.entityManagerFactory(emf)
				.build();
	}
}
