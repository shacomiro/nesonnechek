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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.api.global.error.JwtException;
import com.shacomiro.makeabook.api.global.security.dto.TokenResponse;
import com.shacomiro.makeabook.api.global.security.policy.AuthenticationScheme;
import com.shacomiro.makeabook.api.global.security.userdetails.CustomUserDetails;
import com.shacomiro.makeabook.domain.redis.token.entity.JwtToken;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRepository;

import io.jsonwebtoken.Claims;

public class JwtReissueFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final JwtTokenRepository jwtTokenRepository;
	private final ObjectMapper objectMapper;
	private RequestMatcher reissueRequestMatcher;

	public JwtReissueFilter(JwtProvider jwtProvider, JwtTokenRepository jwtTokenRepository, ObjectMapper objectMapper) {
		this.jwtProvider = jwtProvider;
		this.jwtTokenRepository = jwtTokenRepository;
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
					emailValue = ((CustomUserDetails)auth.getPrincipal()).getEmail();
				} else {
					throw new JwtException("User Authentication info not found.");
				}

				String accessToken = jwtProvider.createAccessToken(emailValue);
				String refreshToken = jwtTokenRepository.findByKeyAndType(emailValue, "refresh")
						.map(JwtToken::getToken)
						.orElseThrow(() -> new JwtException("JWT refresh token is already expired."));
				Claims claims = jwtProvider.parseClaims(accessToken);

				String savedAccessToken = jwtTokenRepository.save(
						JwtToken.byAllParameter()
								.id(claims.getId())
								.key(emailValue)
								.type(claims.get("typ", String.class))
								.token(accessToken)
								.expiration(1000L * 60 * 60 * 2)
								.build()
				).getToken();

				TokenResponse tokenResponse = new TokenResponse(
						HttpHeaders.AUTHORIZATION,
						AuthenticationScheme.BEARER.getType(),
						savedAccessToken,
						refreshToken
				);

				jwtReissueSuccessHandle(request, response, tokenResponse);
			} catch (RuntimeException e) {
				jwtReissueExceptionHandle(request, response, e);
			}

			return;
		}

		filterChain.doFilter(request, response);
	}

	public void jwtReissueSuccessHandle(HttpServletRequest request, HttpServletResponse response,
			TokenResponse tokenResponse) throws IOException {
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		response.getWriter().write(objectMapper.writeValueAsString(tokenResponse));
		response.getWriter().flush();
	}

	public void jwtReissueExceptionHandle(HttpServletRequest request, HttpServletResponse response,
			RuntimeException exception) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		String errorMessage = objectMapper.writeValueAsString(error("Reissue error", HttpStatus.UNAUTHORIZED));

		response.getWriter().write(errorMessage);
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
