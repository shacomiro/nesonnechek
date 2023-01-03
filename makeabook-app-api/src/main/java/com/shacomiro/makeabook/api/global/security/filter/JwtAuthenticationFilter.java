package com.shacomiro.makeabook.api.global.security.filter;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.domain.token.exception.JwtException;
import com.shacomiro.makeabook.domain.token.service.JwtService;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException,
			IOException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (token != null) {
			try {
				token = jwtService.getBearerToken(token);
				Claims claims = jwtService.getVerifiedJwtClaims(token);
				verifyJwtTokenRequest(claims.get("typ", String.class), request.getRequestURI());

				Authentication authentication = jwtService.getAuthenticationFromJwt(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (UsernameNotFoundException | JwtException e) {
				jwtExceptionHandle(request, response, e);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	public void jwtExceptionHandle(HttpServletRequest request, HttpServletResponse response,
			RuntimeException exception) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		response.getWriter().write(objectMapper.writeValueAsString(error(exception.getMessage(), HttpStatus.UNAUTHORIZED)));
		response.getWriter().flush();
	}

	private void verifyJwtTokenRequest(String type, String requestUri) {
		boolean isRefreshToken = type.equals("refresh");
		boolean isReissueUrl = requestUri.equals("/api/sign/reissue");

		if ((isRefreshToken && !isReissueUrl) || (!isRefreshToken && isReissueUrl)) {
			throw new JwtException("Unacceptable JWT token");
		}
	}
}
