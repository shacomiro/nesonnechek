package com.shacomiro.nesonnechek.domain.rds.global.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan({"com.shacomiro.nesonnechek.domain.rds"})
@EnableJpaRepositories({"com.shacomiro.nesonnechek.domain.rds"})
public class JpaConfiguration {
}
