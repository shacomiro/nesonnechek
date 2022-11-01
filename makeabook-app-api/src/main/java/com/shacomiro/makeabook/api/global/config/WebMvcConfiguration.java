package com.shacomiro.makeabook.api.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shacomiro.makeabook.api.global.config.formatter.EpubVersionFormatter;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		WebMvcConfigurer.super.addFormatters(registry);
		registry.addFormatter(new EpubVersionFormatter());
	}
}
