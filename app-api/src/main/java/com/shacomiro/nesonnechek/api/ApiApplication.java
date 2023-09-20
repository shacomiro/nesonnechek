package com.shacomiro.nesonnechek.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
		"com.shacomiro.nesonnechek.api",
		"com.shacomiro.nesonnechek.domain",
		"com.shacomiro.nesonnechek.cache"
})
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
