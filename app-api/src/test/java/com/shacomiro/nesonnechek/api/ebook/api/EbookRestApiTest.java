package com.shacomiro.nesonnechek.api.ebook.api;

import static com.shacomiro.nesonnechek.api.global.config.mock.doc.descriptor.ReusableFieldDescriptor.*;
import static com.shacomiro.nesonnechek.api.global.config.mock.doc.descriptor.ReusableLinkDescriptor.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.FileInputStream;

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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.nesonnechek.api.ebook.dto.model.EbookModel;
import com.shacomiro.nesonnechek.api.global.config.mock.doc.RestDocsConfiguration;
import com.shacomiro.nesonnechek.api.global.security.dto.JwtDto;
import com.shacomiro.nesonnechek.api.global.security.service.JwtProvisionService;

@SpringBootTest
@Import(RestDocsConfiguration.class)
@ExtendWith(value = {RestDocumentationExtension.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
class EbookRestApiTest {
	private static final String USER1_EMAIL = "user1@email.com";
	@Autowired
	private RestDocumentationResultHandler restDocs;
	@Autowired
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	private JwtDto jwtDto;

	@BeforeEach
	void setUp(final RestDocumentationContextProvider provider, final WebApplicationContext context,
			@Autowired JwtProvisionService jwtProvisionService) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(provider))
				.apply(springSecurity())
				.alwaysDo(restDocs)
				.build();
		jwtDto = jwtProvisionService.issueJwt(USER1_EMAIL);
	}

	@Test
	@Order(1)
	@DisplayName("txt 전자책 생성")
	void createTxtEbook() throws Exception {
		//given
		String url = "/api/v1/ebooks/txt-ebook";
		String bearerToken = "Bearer " + jwtDto.getAccessToken();
		String ebookType = "epub2";
		MockMultipartFile file = new MockMultipartFile("txtFile", "lorem-ipsum.txt",
				MimeTypeUtils.TEXT_PLAIN_VALUE, new FileInputStream("./src/test/resources/lorem-ipsum.txt"));

		//when
		ResultActions result = mockMvc.perform(
				multipart(url).file(file)
						.header(HttpHeaders.AUTHORIZATION, bearerToken)
						.param("type", ebookType)
		);

		//then
		result.andExpect(status().isCreated())
				.andDo(print())
				.andDo(restDocs.document(
						links(CommonLinkDescriptor.SELF_LINK_DESCR)
								.and(EbookLinkDescriptor.EBOOK_MODEL_LINKS_DESCR_LIST)
								.and(CommonLinkDescriptor.DOCS_LINK_DESCR),
						responseFields(EbookFieldDescriptor.EBOOK_MODEL_RES_FIELD_DESCR_LIST)
				));
	}

	@Test
	@Order(2)
	@DisplayName("전자책 조회")
	void getEbook() throws Exception {
		//given
		String uuid = "550e8400-e29b-41d4-a716-446655440001";
		String url = "/api/v1/ebooks/" + uuid;
		String bearerToken = "Bearer " + jwtDto.getAccessToken();

		//when
		ResultActions result = mockMvc.perform(
				get(url).header(HttpHeaders.AUTHORIZATION, bearerToken)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(print())
				.andDo(restDocs.document(
						links(CommonLinkDescriptor.SELF_LINK_DESCR)
								.and(EbookLinkDescriptor.EBOOK_MODEL_LINKS_DESCR_LIST)
								.and(CommonLinkDescriptor.DOCS_LINK_DESCR),
						responseFields(EbookFieldDescriptor.EBOOK_MODEL_RES_FIELD_DESCR_LIST)
				));
	}

	@Test
	@Order(3)
	@DisplayName("txt 전자책 생성 후 다운로드")
	void downloadEbook() throws Exception {
		//given
		String url1 = "/api/v1/ebooks/txt-ebook";
		String bearerToken = "Bearer " + jwtDto.getAccessToken();
		String ebookType = "epub2";
		MockMultipartFile file = new MockMultipartFile("txtFile", "lorem-ipsum.txt",
				MimeTypeUtils.TEXT_PLAIN_VALUE, new FileInputStream("./src/test/resources/lorem-ipsum.txt"));

		String ebookContent = mockMvc.perform(
				multipart(url1).file(file)
						.header(HttpHeaders.AUTHORIZATION, bearerToken)
						.param("type", ebookType)
		).andReturn().getResponse().getContentAsString();
		EbookModel ebookModel = objectMapper.readValue(ebookContent, EbookModel.class);

		String url2 = "/api/v1/ebooks/" + ebookModel.getUuid() + "/file";

		//when
		ResultActions result = mockMvc.perform(
				get(url2).header(HttpHeaders.AUTHORIZATION, bearerToken)
		);

		//then
		result.andExpect(status().isOk())
				.andDo(print())
				.andDo(restDocs.document());
	}
}
