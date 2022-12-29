package com.shacomiro.makeabook.api.global.security;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.log.LogMessage;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRepository;

public class JwtLogoutFilter extends OncePerRequestFilter {
	private final JwtLogoutHandler jwtLogoutHandler;
	private final JwtLogoutSucessHandler jwtLogoutSucessHandler;
	private final ObjectMapper objectMapper;
	private RequestMatcher logoutRequestMatcher;

	public JwtLogoutFilter(JwtTokenRepository jwtTokenRepository, ObjectMapper objectMapper) {
		this.jwtLogoutHandler = new JwtLogoutHandler(jwtTokenRepository);
		this.jwtLogoutSucessHandler = new JwtLogoutSucessHandler(objectMapper);
		this.objectMapper = objectMapper;
		setFilterProcessesUrl("/api/sign/signout");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException,
			IOException {
		if (requiresLogout(request, response)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (this.logger.isDebugEnabled()) {
				this.logger.debug(LogMessage.format("Signing out [%s]", auth));
			}

			try {
				jwtLogoutHandler.logout(request, response, auth);
				jwtLogoutSucessHandler.onLogoutSuccess(request, response, auth);
			} catch (RuntimeException e) {
				jwtLogoutExceptionHandle(request, response, e);
			}

			return;
		}

		filterChain.doFilter(request, response);
	}

	public void jwtLogoutExceptionHandle(HttpServletRequest request, HttpServletResponse response,
			RuntimeException exception) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String errorMessage = objectMapper.writeValueAsString(error("sign out error", HttpStatus.UNAUTHORIZED));

		response.getWriter().write(errorMessage);
		response.getWriter().flush();
	}

	protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
		if (this.logoutRequestMatcher.matches(request)) {
			return true;
		}
		if (this.logger.isTraceEnabled()) {
			this.logger.trace(LogMessage.format("Did not match request to %s", this.logoutRequestMatcher));
		}
		return false;
	}

	public void setLogoutRequestMatcher(RequestMatcher logoutRequestMatcher) {
		Assert.notNull(logoutRequestMatcher, "logoutRequestMatcher cannot be null");
		this.logoutRequestMatcher = logoutRequestMatcher;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.logoutRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
	}
}
