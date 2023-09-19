package com.shacomiro.makeabook.batch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@EntityScan({"com.shacomiro.makeabook.domain.rds"})
@EnableJpaRepositories({"com.shacomiro.makeabook.domain.rds"})
@EnableTransactionManagement
public class TestBatchConfig {
}
