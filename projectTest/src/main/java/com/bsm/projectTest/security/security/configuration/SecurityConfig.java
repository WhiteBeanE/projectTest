package com.bsm.projectTest.security.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic().disable() // HTTP 기본 인증을 비활성화
			.csrf().disable() // csrf 보호 기능을 비활성화
		    .cors() // cors 설정을 활성화
	    	.and()
	    	// 인증 허용 범위 설정
	    	.authorizeRequests()
		    	.antMatchers("/login").permitAll()
	    		.antMatchers("/dt/**").hasAnyRole("doctor")
	    		.antMatchers("/**").authenticated()
	    		.and()
	    	.formLogin()
	    		.loginPage("/login")
	    		.loginProcessingUrl("/login")
	    		.usernameParameter("memberId")
				.passwordParameter("password")
	    		.and()
    		.logout()
				.logoutUrl("/standard/logout")
				.logoutSuccessUrl("/")
				.and();
		return httpSecurity.build();
	}
}
