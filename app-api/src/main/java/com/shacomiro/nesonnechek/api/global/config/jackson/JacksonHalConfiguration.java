package com.shacomiro.nesonnechek.api.global.config.jackson;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.MessageResolver;
import org.springframework.hateoas.mediatype.hal.CurieProvider;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonHalConfiguration extends AbstractJackson2HttpMessageConverter {

	public JacksonHalConfiguration(ObjectMapper objectMapper) {
		super(objectMapper, MediaTypes.HAL_JSON);
		Jackson2HalModule jackson2HalModule = new Jackson2HalModule();
		objectMapper.registerModule(jackson2HalModule);
		objectMapper.setHandlerInstantiator(
				new Jackson2HalModule.HalHandlerInstantiator(
						new AnnotationLinkRelationProvider(), CurieProvider.NONE, MessageResolver.DEFAULTS_ONLY));
	}
}
