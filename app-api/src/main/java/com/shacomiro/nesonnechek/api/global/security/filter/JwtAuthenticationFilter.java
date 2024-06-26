package com.shacomiro.nesonnechek.api.global.security.filter;

import static com.shacomiro.nesonnechek.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.jwt.policy.SecurityScheme;
import com.shacomiro.nesonnechek.api.global.security.token.JwtAuthenticationToken;

import io.jsonwebtoken.JwtException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper;
	private RequestMatcher docsRequestMatcher;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
		this.authenticationManager = authenticationManager;
		this.objectMapper = objectMapper;
		setFilterProcessesUrl("/docs/**");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException,
			IOException {
		String token = resolveToken(request);

		if (docsReissue(request, response)) {
			token = null;
		}

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
		boolean isReissueUrl = requestUri.equals("/auth/reissue");

		if ((isRefreshToken && !isReissueUrl) || (!isRefreshToken && isReissueUrl)) {
			throw new JwtException("Unacceptable JWT token");
		}
	}

	protected boolean docsReissue(HttpServletRequest request, HttpServletResponse response) {
		if (this.docsRequestMatcher.matches(request)) {
			return true;
		}
		if (this.logger.isTraceEnabled()) {
			this.logger.trace(LogMessage.format("Did not match request to %s", this.docsRequestMatcher));
		}
		return false;
	}

	public void setDocsRequestMatcher(RequestMatcher reissueRequestMatcher) {
		Assert.notNull(reissueRequestMatcher, "reissueRequestMatcher cannot be null");
		this.docsRequestMatcher = reissueRequestMatcher;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.docsRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
	}
}
