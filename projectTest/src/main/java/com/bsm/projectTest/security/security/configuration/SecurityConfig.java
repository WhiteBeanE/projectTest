package com.bsm.projectTest.security.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bsm.projectTest.jwt.handler.JwtAuthenticationFilter;
import com.bsm.projectTest.jwt.handler.JwtFilter;
import com.bsm.projectTest.jwt.handler.JwtProvider;
import com.bsm.projectTest.jwt.service.JwtService;
import com.bsm.projectTest.security.security.handler.UserLoginFailureHandler;
import com.bsm.projectTest.security.security.handler.UserLoginSuccessHandler;
import com.bsm.projectTest.security.service.Impl.MemberDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	// JWT용 시큐리티 재설정 기존 시큐리티 하단 주석처리
	private final JwtProvider jwtProvider;
	private final JwtService jwtService;
	private final JwtFilter jwtFilter;
	private final UserLoginSuccessHandler userLoginSuccessHandler;
	private final UserLoginFailureHandler userLoginFailureHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic().disable() // HTTP 기본 인증을 비활성화
			.csrf().disable() // csrf 보호 기능을 비활성화
			// JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	    		.and()
//	    	.formLogin()
//	    		.loginPage("/login")
//	    		.loginProcessingUrl("/jwt/login")
//	    		.usernameParameter("memberId")
//				.passwordParameter("password")
//				.successHandler(userLoginSuccessHandler) // 로그인 성공시 실행되는 핸들러
//				.failureHandler(userLoginFailureHandler) // 로그인 실패시 실행되는 핸들러
//	    		.and()
	    	// 인증 허용 범위 설정
	    	.authorizeRequests()
		    	.antMatchers("/jwt/login").permitAll() //POST 로그인 요청
				.antMatchers("/dt/**").hasAnyRole("doctor")
				.anyRequest().authenticated()
				.and()
			// 보안 필터 체인에 사용자 정의 필터를 추가하는 역할
			// 기본 인증 필터 중 하나인 UsernamePasswordAuthenticationFilter 이전에 사용자 정의 필터인 JwtAuthenticationFilter를 실행하도록 설정
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
	
	@Bean
	// JWT를 사용하기 위해 기본적인 password encoder가 필요
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity
//			.httpBasic().disable() // HTTP 기본 인증을 비활성화
//			.csrf().disable() // csrf 보호 기능을 비활성화
//		    .cors() // cors 설정을 활성화
//	    	.and()
//	    	// 인증 허용 범위 설정
//	    	.authorizeRequests()
//		    	.antMatchers("/login").permitAll()
//	    		.antMatchers("/dt/**").hasAnyRole("doctor")
//	    		.antMatchers("/**").authenticated()
//	    		.and()
//	    	.formLogin()
//	    		.loginPage("/login")
//	    		.loginProcessingUrl("/login")
//	    		.usernameParameter("memberId")
//				.passwordParameter("password")
//	    		.and()
//    		.logout()
//				.logoutUrl("/standard/logout")
//				.logoutSuccessUrl("/")
//				.and();
//		return httpSecurity.build();
//	}
}
