package com.bsm.projectTest.security.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bsm.projectTest.jwt.handler.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	// JWT용 시큐리티 재설정 기존 시큐리티 하단 주석처리
	private final JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic().disable() // HTTP 기본 인증을 비활성화
			.csrf().disable() // csrf 보호 기능을 비활성화
			.formLogin().disable()
			// JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    		.and()
	    	// 인증 허용 범위 설정
	    	.authorizeRequests()
		    	.antMatchers("/jwt/login").permitAll()
				.antMatchers("/dt/**").hasAnyRole("doctor")
				.anyRequest().authenticated()
				.and()
			// 보안 필터 체인에 사용자 정의 필터를 추가하는 역할
			// 기본 인증 필터 중 하나인 UsernamePasswordAuthenticationFilter 이전에 사용자 정의 필터인 JwtAuthenticationFilter를 실행하도록 설정
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.headers()
			.defaultsDisabled()
			.contentTypeOptions();
		return httpSecurity.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}