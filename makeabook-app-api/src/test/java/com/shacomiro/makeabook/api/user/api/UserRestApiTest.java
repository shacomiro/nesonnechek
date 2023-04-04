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

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.api.global.config.mock.doc.RestDocsConfiguration;
import com.shacomiro.makeabook.api.global.security.dto.JwtDto;
import com.shacomiro.makeabook.api.global.security.filter.JwtAuthenticationFilter;
import com.shacomiro.makeabook.api.global.security.filter.JwtReissueFilter;
import com.shacomiro.makeabook.api.global.security.service.JwtProvisionService;
import com.shacomiro.makeabook.api.user.dto.DeleteUserRequest;
import com.shacomiro.makeabook.api.user.dto.UpdateUserRequest;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(value = {RestDocumentationExtension.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
class UserRestApiTest {
	private static final String USER_EMAIL = "user1@email.com";
	@Autowired
	private RestDocumentationResultHandler restDocs;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private JwtProvisionService jwtProvisionService;
	private MockMvc mockMvc;
	private JwtDto jwtDto;

	@BeforeEach
	void setUp(
			final RestDocumentationContextProvider provider, final WebApplicationContext context,
			@Autowired AuthenticationManager authenticationManager
	) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(provider))
				.apply(springSecurity())
				.alwaysDo(print())
				.alwaysDo(restDocs)
				.addFilter(new JwtAuthenticationFilter(authenticationManager, objectMapper))
				.addFilter(new JwtReissueFilter(jwtProvisionService, objectMapper))
				.addFilters(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
				.build();
		jwtDto = jwtProvisionService.issueJwt(USER_EMAIL);
	}

	@AfterEach
	void tearDown() {
		jwtDto = null;
	}

	@Test
	@Order(1)
	@DisplayName("유저 정보 조회")
	void getAccountInfo() throws Exception {
		//given
		String url = "/api/v1/users/account";
		String bearerToken = "Bearer " + jwtDto.getAccessToken();

		//when
		ResultActions result = mockMvc.perform(
				get(url).header(HttpHeaders.AUTHORIZATION, bearerToken)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(restDocs.document(
						links(CommonLinkDescriptor.SELF_LINK_DESCR)
								.and(UserLinkDescriptor.USER_MODEL_LINKS_DESCR_LIST)
								.and(CommonLinkDescriptor.DOCS_LINK_DESCR),
						responseFields(UserFieldDescriptor.USER_MODEL_RES_FIELD_DESCR_LIST)
				));
	}

	@Test
	@Order(2)
	@DisplayName("유저 정보 수정")
	void updateAccountInfo() throws Exception {
		//given
		String url = "/api/v1/users/account";
		String bearerToken = "Bearer " + jwtDto.getAccessToken();
		String content = objectMapper.writeValueAsString(new UpdateUserRequest("user1_new_password", "USER1_NEW_USERNAME"));

		//when
		ResultActions result = mockMvc.perform(
				put(url).header(HttpHeaders.AUTHORIZATION, bearerToken)
						.content(content)
						.contentType(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(restDocs.document(
						links(CommonLinkDescriptor.SELF_LINK_DESCR)
								.and(UserLinkDescriptor.USER_MODEL_LINKS_DESCR_LIST)
								.and(CommonLinkDescriptor.DOCS_LINK_DESCR),
						responseFields(UserFieldDescriptor.USER_MODEL_RES_FIELD_DESCR_LIST)
				));
	}

	@Test
	@Order(3)
	@DisplayName("유저 삭제")
	void deleteAccount() throws Exception {
		//given
		String url = "/api/v1/users/account";
		JwtDto nonEbookUserjwtDto = jwtProvisionService.issueJwt("user5@email.com");
		String bearerToken = "Bearer " + nonEbookUserjwtDto.getAccessToken();
		String content = objectMapper.writeValueAsString(new DeleteUserRequest("user5_password"));

		//when
		ResultActions result = mockMvc.perform(
				delete(url).header(HttpHeaders.AUTHORIZATION, bearerToken)
						.content(content)
						.contentType(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(restDocs.document());
	}

	@Test
	@Order(4)
	@DisplayName("현재 유저 전자책 조회")
	void getAccountEbooks() throws Exception {
		//given
		String url = "/api/v1/users/account/ebooks";
		String bearerToken = "Bearer " + jwtDto.getAccessToken();

		//when
		ResultActions result = mockMvc.perform(
				get(url).header(HttpHeaders.AUTHORIZATION, bearerToken)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(restDocs.document(
						links(CommonLinkDescriptor.SELF_LINK_DESCR)
								.and(EbookLinkDescriptor.EBOOK_MODEL_LINKS_DESCR_LIST)
								.and(UserLinkDescriptor.USER_MODEL_LINKS_DESCR_LIST)
								.and(CommonLinkDescriptor.DOCS_LINK_DESCR)
								.and(CommonLinkDescriptor.PAGE_LINKS_DESCR_LIST),
						responseFields(EbookFieldDescriptor.EBOOK_COL_MODEL_RES_FIELD_DESCR_LIST)
								.and(CommonFieldDescriptor.LINKS_FIELD_DESCR)
								.and(CommonFieldDescriptor.PAGE_FIELD_DESCR)
				));
	}

	@Test
	@Order(5)
	@DisplayName("현재 유저 전자책 삭제")
	void deleteAllAccountEbooks() throws Exception {
		//given
		String url = "/api/v1/users/account/ebooks";
		JwtDto nonEbookUserjwtDto = jwtProvisionService.issueJwt("user2@email.com");
		String bearerToken = "Bearer " + nonEbookUserjwtDto.getAccessToken();
		String content = objectMapper.writeValueAsString(new DeleteUserRequest("user2_password"));

		//when
		ResultActions result = mockMvc.perform(
				delete(url).header(HttpHeaders.AUTHORIZATION, bearerToken)
						.content(content)
						.contentType(MediaType.APPLICATION_JSON)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(restDocs.document());
	}
}
