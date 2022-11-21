package com.shacomiro.makeabook.api.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shacomiro.makeabook.api.global.config.formatter.EbookExtensionFormatter;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		WebMvcConfigurer.super.addFormatters(registry);
		registry.addFormatter(new EbookExtensionFormatter());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/api/static/docs/**")
				.addResourceLocations("classpath:/static/docs/");
	}

	// @Bean
	// ForwardedHeaderTransformer forwardedHeaderFilter() {
	// 	return new ForwardedHeaderTransformer();
	// }
}
