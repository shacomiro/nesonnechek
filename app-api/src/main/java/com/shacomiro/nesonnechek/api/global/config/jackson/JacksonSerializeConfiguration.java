package com.shacomiro.nesonnechek.api.global.config.jackson;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.shacomiro.nesonnechek.api.global.security.deserializer.SimpleGrantedAuthorityDeserializer;

@Configuration
public class JacksonSerializeConfiguration {

	public JacksonSerializeConfiguration(ObjectMapper objectMapper) {
		SimpleModule grantedAuthorityDeserializeModule = new SimpleModule()
				.addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer());
		objectMapper.registerModule(grantedAuthorityDeserializeModule);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
}
