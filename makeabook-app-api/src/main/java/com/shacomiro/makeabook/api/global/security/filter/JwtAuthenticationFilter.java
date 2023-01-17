package com.shacomiro.makeabook.api.global.security.filter;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.jwt.policy.SecurityScheme;
import com.shacomiro.makeabook.api.global.security.token.JwtAuthenticationToken;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException,
			IOException {
		String token = resolveToken(request);

		if (token != null) {
			try {
				Authentication jwtAuthenticationToken = JwtAuthenticationToken.unauthenticated(token);
				Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
				verifyJwtTokenRequest(((JwtAuthenticationToken)authentication).getType(), request.getRequestURI());
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

	private String resolveToken(HttpServletRequest request) {
		String rawToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.isEmpty(rawToken)) {
			return null;
		}

		if (rawToken.startsWith(SecurityScheme.BEARER_AUTH.getPrefix())) {
			return rawToken.replaceFirst(SecurityScheme.BEARER_AUTH.getPrefix(), "");
		}

		return null;
	}

	private void verifyJwtTokenRequest(String type, String requestUri) {
		boolean isRefreshToken = type.equals("refresh");
		boolean isReissueUrl = requestUri.equals("/api/sign/reissue");

		if ((isRefreshToken && !isReissueUrl) || (!isRefreshToken && isReissueUrl)) {
			throw new JwtException("Unacceptable JWT token");
		}
	}
}
