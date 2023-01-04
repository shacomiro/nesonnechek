package com.shacomiro.makeabook.api.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shacomiro.makeabook.api.global.security.filter.JwtAuthenticationFilter;
import com.shacomiro.makeabook.api.global.security.filter.JwtReissueFilter;
import com.shacomiro.makeabook.api.global.security.handler.JwtAccessDeniedHandler;
import com.shacomiro.makeabook.api.global.security.handler.JwtAuthenticationEntryPoint;
import com.shacomiro.makeabook.api.global.security.provider.JwtAuthenticationProvider;
import com.shacomiro.makeabook.domain.token.service.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);

		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http
				.httpBasic().disable()
				.csrf().disable()
				.formLogin().disable()
				.logout().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint(objectMapper))
				.accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper))
				.and()
				.addFilterBefore(new JwtAuthenticationFilter(authenticationManager, objectMapper),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new JwtReissueFilter(jwtService, objectMapper), FilterSecurityInterceptor.class);

		return http.build();
	}
}
