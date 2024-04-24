package com.shacomiro.nesonnechek.api.global.config.jackson;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shacomiro.nesonnechek.api.global.security.deserializer.SimpleGrantedAuthorityDeserializer;

@Configuration
public class JacksonSerializeConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> builder.deserializerByType(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer())
				.featuresToEnable(SerializationFeature.INDENT_OUTPUT)
				.featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
	}
}
