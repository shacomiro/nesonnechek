package com.shacomiro.makeabook.batch.job;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

import com.shacomiro.makeabook.domain.rds.ebook.entity.Ebook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class DeleteExpiredEbookFileJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory emf;
	private static final int CHUNK_SIZE = 10;

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

			if (ebook.isExpired()) {
				Path targetPath = Paths.get("./files/ebook/", ebook.getType() + "/",
						ebook.getUuid() + "." + ebook.getExtension()).normalize().toAbsolutePath();
				boolean isDeleted = Files.deleteIfExists(targetPath);
				if (isDeleted) {
					ebook.updateExists();
				}
				log.info(
						">>> EbookFile(uuid={}, filename={}, expiredAt={}) is expired! :: (isExist={}, isExpired={}, isDeleted={})",
						ebook.getUuid(), ebook.getName(), ebook.getExpiredAt(), ebook.isExist(),
						ebook.isExpired(),
						isDeleted);

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
