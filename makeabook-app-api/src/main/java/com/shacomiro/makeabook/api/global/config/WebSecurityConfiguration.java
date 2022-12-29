package com.shacomiro.makeabook.api.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.api.global.security.JwtAccessDeniedHandler;
import com.shacomiro.makeabook.api.global.security.JwtAuthenticationEntryPoint;
import com.shacomiro.makeabook.api.global.security.JwtAuthenticationFilter;
import com.shacomiro.makeabook.api.global.security.JwtLogoutFilter;
import com.shacomiro.makeabook.api.global.security.JwtProvider;
import com.shacomiro.makeabook.domain.redis.token.repository.JwtTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
	private final JwtProvider jwtProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtTokenRepository jwtTokenRepository;
	private final ObjectMapper objectMapper;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic().disable()
				.csrf().disable()
				.formLogin().disable()
				.logout().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
				.and()
				.addFilterBefore(new JwtAuthenticationFilter(jwtProvider, jwtTokenRepository, objectMapper),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new JwtLogoutFilter(jwtTokenRepository, objectMapper),
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
