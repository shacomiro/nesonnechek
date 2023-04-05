package com.shacomiro.makeabook.api.user.api;

import static com.shacomiro.makeabook.api.global.config.mock.doc.descriptor.ReusableFieldDescriptor.*;
import static com.shacomiro.makeabook.api.global.config.mock.doc.descriptor.ReusableLinkDescriptor.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.api.global.config.mock.doc.RestDocsConfiguration;
import com.shacomiro.makeabook.api.user.dto.SignInRequest;
import com.shacomiro.makeabook.api.user.dto.SignUpRequest;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(value = {RestDocumentationExtension.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
class AuthenticationRestApiTest {
	@Autowired
	private RestDocumentationResultHandler restDocs;
	@Autowired
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp(final RestDocumentationContextProvider provider, final WebApplicationContext context) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(provider))
				.apply(springSecurity())
				.alwaysDo(print())
				.alwaysDo(restDocs)
				.build();
	}

	@Test
	@Order(1)
	@DisplayName("유저 로그인")
	void singIn() throws Exception {
		//given
		String url = "/api/v1/auth/sign-in";
		String email = "user1@email.com";
		String password = "user1_password";
		String content = objectMapper.writeValueAsString(new SignInRequest(email, password));

		//when
		ResultActions result = mockMvc.perform(
				post(url).content(content)
						.contentType(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(restDocs.document(
						links(JwtLinkDescriptor.JWT_MODEL_LINKS_DESCR_LIST)
								.and(CommonLinkDescriptor.DOCS_LINK_DESCR),
						responseFields(JwtFieldDescriptor.JWT_MODEL_RES_FIELD_DESCR_LIST)
				));
	}

	@Test
	@Order(2)
	@DisplayName("유저 회원가입")
	void signUp() throws Exception {
		//given
		String url = "/api/v1/auth/sign-up";
		String email = "new_user@email.com";
		String password = "new_user_password";
		String username = "NEW_USER";
		String content = objectMapper.writeValueAsString(new SignUpRequest(email, password, username));

		//when
		ResultActions result = mockMvc.perform(
				post(url).content(content)
						.contentType(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isCreated())
				.andDo(restDocs.document(
						links(CommonLinkDescriptor.SELF_LINK_DESCR)
								.and(UserLinkDescriptor.USER_MODEL_LINKS_DESCR_LIST)
								.and(CommonLinkDescriptor.DOCS_LINK_DESCR),
						responseFields(UserFieldDescriptor.USER_SIGNUP_MODEL_RES_FIELD_DESCR_LIST)
				));
	}
}
