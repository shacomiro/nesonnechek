package com.shacomiro.makeabook.api.global.security.filter;

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
import com.shacomiro.makeabook.domain.token.dto.JwtDto;
import com.shacomiro.makeabook.domain.token.exception.JwtException;
import com.shacomiro.makeabook.domain.token.service.JwtService;
import com.shacomiro.makeabook.domain.user.vo.UserPrincipal;

public class JwtReissueFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;
	private RequestMatcher reissueRequestMatcher;

	public JwtReissueFilter(JwtService jwtService, ObjectMapper objectMapper) {
		this.jwtService = jwtService;
		this.objectMapper = objectMapper;
		setFilterProcessesUrl("/api/sign/reissue");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException,
			IOException {
		if (requiresReissue(request, response)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (this.logger.isDebugEnabled()) {
				this.logger.debug(LogMessage.format("Reissuing [%s]", auth));
			}
			try {
				String emailValue;
				if (auth != null) {
					emailValue = ((UserPrincipal)auth.getPrincipal()).getEmail();
				} else {
					throw new JwtException("User Authentication info not found.");
				}
				String token = jwtService.getBearerToken(jwtService.resolveJwtFromRequest(request));
				jwtService.verifyRefreshJwt(emailValue, token);
				JwtDto jwtDto = jwtService.reissueJwt(emailValue);
				jwtReissueSuccessHandle(request, response, jwtDto);
			} catch (RuntimeException e) {
				jwtReissueExceptionHandle(request, response, e);
			}

			return;
		}

		filterChain.doFilter(request, response);
	}

	public void jwtReissueSuccessHandle(HttpServletRequest request, HttpServletResponse response,
			JwtDto jwtDto) throws IOException {
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		response.getWriter().write(objectMapper.writeValueAsString(jwtDto));
		response.getWriter().flush();
	}

	public void jwtReissueExceptionHandle(HttpServletRequest request, HttpServletResponse response,
			RuntimeException exception) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		response.getWriter().write(objectMapper.writeValueAsString(error(exception.getMessage(), HttpStatus.UNAUTHORIZED)));
		response.getWriter().flush();
	}

	protected boolean requiresReissue(HttpServletRequest request, HttpServletResponse response) {
		if (this.reissueRequestMatcher.matches(request)) {
			return true;
		}
		if (this.logger.isTraceEnabled()) {
			this.logger.trace(LogMessage.format("Did not match request to %s", this.reissueRequestMatcher));
		}
		return false;
	}

	public void setReissueRequestMatcher(RequestMatcher reissueRequestMatcher) {
		Assert.notNull(reissueRequestMatcher, "reissueRequestMatcher cannot be null");
		this.reissueRequestMatcher = reissueRequestMatcher;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.reissueRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
	}
}
