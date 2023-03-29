package com.shacomiro.makeabook.api.user.api;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.api.global.security.dto.JwtDto;
import com.shacomiro.makeabook.api.global.security.filter.JwtAuthenticationFilter;
import com.shacomiro.makeabook.api.global.security.filter.JwtReissueFilter;
import com.shacomiro.makeabook.api.global.security.service.JwtProvisionService;

@SpringBootTest
@ExtendWith(value = {RestDocumentationExtension.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
class UserRestApiTest {
	private static final String USER_EMAIL = "user1@email.com";
	private MockMvc mockMvc;
	private JwtDto jwtDto;

	@BeforeEach
	void setUp(
			final RestDocumentationContextProvider restDocumentation, final WebApplicationContext context,
			@Autowired AuthenticationManager authenticationManager, @Autowired ObjectMapper objectMapper,
			@Autowired JwtProvisionService jwtProvisionService
	) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation))
				.apply(springSecurity())
				.addFilter(new JwtAuthenticationFilter(authenticationManager, objectMapper))
				.addFilter(new JwtReissueFilter(jwtProvisionService, objectMapper))
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
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
				.andDo(print())
				.andDo(document("{class-name}/{method-name}",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						links(
								linkWithRel("self").description("Link to self"),
								linkWithRel("docs").description("Link to API documentation")
						),
						responseFields(
								fieldWithPath("email").description("User's email").type(JsonFieldType.STRING),
								fieldWithPath("username").description("User's name").type(JsonFieldType.STRING),
								fieldWithPath("loginCount").description("User's login count number").type(JsonFieldType.NUMBER),
								fieldWithPath("lastLoginAt").description("Last login date").type(JsonFieldType.STRING),
								fieldWithPath("createdAt").description("Account creation date").type(JsonFieldType.STRING),
								subsectionWithPath("_links").description("HATEOAS link").type(JsonFieldType.OBJECT)
						))
				);
	}
}
