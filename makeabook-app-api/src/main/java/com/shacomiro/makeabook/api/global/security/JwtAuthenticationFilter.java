package com.shacomiro.makeabook.api.global.security;

import static com.shacomiro.makeabook.api.global.util.ApiUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
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

				if ((jwtProvider.parseClaims(token).get("typ", String.class).equals("refresh") &&
						!request.getRequestURI().equals("/api/sign/reissue")) ||
						(!jwtProvider.parseClaims(token).get("typ", String.class).equals("refresh")
								&& request.getRequestURI().equals("/api/sign/reissue"))) {
					throw new JwtException("Invalid JWT token");
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
