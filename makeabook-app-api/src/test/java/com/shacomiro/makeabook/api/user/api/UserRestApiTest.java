package com.shacomiro.makeabook.api.user.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.shacomiro.makeabook.api.global.config.mock.annotation.EnableMockMvc;
import com.shacomiro.makeabook.api.global.security.dto.JwtDto;
import com.shacomiro.makeabook.api.global.security.service.JwtProvisionService;

@SpringBootTest
@EnableMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
class UserRestApiTest {
	private static final String USER_EMAIL = "user1@email.com";
	@Autowired
	private MockMvc mockMvc;
	private JwtDto jwtDto;

	@BeforeEach
	void setUp(@Autowired JwtProvisionService jwtProvisionService) {
		jwtDto = jwtProvisionService.issueJwt(USER_EMAIL);
	}

	@AfterEach
	void tearDown() {
		jwtDto = null;
	}
}
