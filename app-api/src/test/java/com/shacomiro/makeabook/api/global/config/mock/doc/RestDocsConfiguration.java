package com.shacomiro.makeabook.api.global.config.mock.doc;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes.Attribute;

@TestConfiguration
public class RestDocsConfiguration {

	public static Attribute field(final String key, final String value) {
		return new Attribute(key, value);
	}

	@Bean
	public RestDocumentationResultHandler write() {
		return MockMvcRestDocumentation.document(
				"{class-name}/{method-name}",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())
		);
	}
}
