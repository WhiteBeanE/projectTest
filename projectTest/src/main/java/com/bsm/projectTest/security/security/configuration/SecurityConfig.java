package com.bsm.projectTest.security.security.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.bsm.projectTest.jwt.handler.JwtFilter;
import com.bsm.projectTest.security.security.handler.CustomAccessDeniedHandler;
import com.bsm.projectTest.security.security.handler.CustomAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	// JWT용 시큐리티 재설정 기존 시큐리티 하단 주석처리
	private final JwtFilter jwtFilter;
	// 인증 되지 않은 사용자 접속 시 처리 (JwtFilter에서 JWT로 인증되지 못하면 실행)
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	// 권한이 없는 사용자 접속 시 처리
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.addAllowedOrigin("http://localhost:3000");
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
		corsConfig.setAllowedHeaders(Arrays.asList("*"));
		corsConfig.setAllowCredentials(true);
		corsConfig.addExposedHeader("Authorization");
		
		httpSecurity
			.httpBasic().disable() // HTTP 기본 인증을 비활성화
			.csrf().disable() // csrf 보호 기능을 비활성화
			.cors().configurationSource(request -> corsConfig)
			.and()
			.formLogin().disable()
			// JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    		.and()
	    	// 인증 허용 범위 설정
	    	.authorizeRequests()
		    	.antMatchers("/jwt/login", "/login", "/jwt/user").permitAll()
				.antMatchers("/dt/**").hasRole("doctor")
				.anyRequest().authenticated()
				.and()
			//exceptionHandling 설정
			.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessDeniedHandler)
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