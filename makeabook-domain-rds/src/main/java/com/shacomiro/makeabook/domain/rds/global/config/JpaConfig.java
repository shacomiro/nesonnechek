package com.shacomiro.makeabook.domain.rds.global.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan({"com.shacomiro.makeabook.domain.rds"})
@EnableJpaRepositories({"com.shacomiro.makeabook.domain.rds"})
public class JpaConfig {
}
