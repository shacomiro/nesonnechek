package com.shacomiro.nesonnechek.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
		"com.shacomiro.nesonnechek.batch",
		"com.shacomiro.nesonnechek.domain"
})
public class BatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}
}
