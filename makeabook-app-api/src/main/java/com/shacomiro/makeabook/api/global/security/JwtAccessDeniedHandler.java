package com.shacomiro.makeabook.api.global.security;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		response.getWriter().write(objectMapper.writeValueAsString(error("Forbidden", HttpStatus.FORBIDDEN)));
		response.getWriter().flush();
	}
}
