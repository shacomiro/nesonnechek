package com.shacomiro.makeabook.api.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.shacomiro.makeabook.api.global.security.deserializer.SimpleGrantedAuthorityDeserializer;

@Configuration
public class JacksonConfiguration {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper()
				.findAndRegisterModules()
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.enable(SerializationFeature.INDENT_OUTPUT)
				.registerModule(new SimpleModule()
						.addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer())
				);
	}
}
