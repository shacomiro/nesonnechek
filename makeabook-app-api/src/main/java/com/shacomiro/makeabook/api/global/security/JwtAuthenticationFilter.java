package com.shacomiro.makeabook.api.global.security;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.api.global.error.JwtException;
import com.shacomiro.makeabook.api.global.security.policy.AuthenticationScheme;
import com.shacomiro.makeabook.domain.redis.token.entity.JwtToken;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final JwtTokenRepository jwtTokenRepository;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
			ServletException,
			IOException {
		String token = jwtProvider.resolveToken(request);

		try {
			if (token != null && token.contains(AuthenticationScheme.BEARER.getType())) {
				token = token.replaceFirst(AuthenticationScheme.BEARER.getType(), "").trim();
				jwtProvider.verifyToken(token);

				Claims claims = jwtProvider.parseClaims(token);
				String subject = claims.getSubject();
				String type = claims.get("typ", String.class);

				List<JwtToken> storedTokenList = jwtTokenRepository.findAllByKeyAndType(subject, type);

				if (storedTokenList.size() > 1) {
					jwtTokenRepository.deleteAll(jwtTokenRepository.findAllByKey(subject));
					throw new JwtException("Multiple JWT access tokens found. Try sign in again.");
				}

				if (storedTokenList.isEmpty() || !storedTokenList.get(0).getToken().equals(token)) {
					throw new JwtException("Expired JWT token");
				}

				boolean isRefreshToken = type.equals("refresh");
				boolean isReissueUrl = request.getRequestURI().equals("/api/sign/reissue");

				if ((isRefreshToken && !isReissueUrl) || (!isRefreshToken && isReissueUrl)) {
					throw new JwtException("Unacceptable JWT token");
				}

				Authentication authentication = jwtProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

			filterChain.doFilter(request, response);
		} catch (UsernameNotFoundException | JwtException e) {
			jwtExceptionHandle(request, response, e);
		}
	}

	public void jwtExceptionHandle(HttpServletRequest request, HttpServletResponse response,
			RuntimeException exception) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaTypes.HAL_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());

		response.getWriter()
				.write(objectMapper.writeValueAsString(error(exception.getMessage(), HttpStatus.UNAUTHORIZED)));
		response.getWriter().flush();
	}
}
