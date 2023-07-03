package com.bsm.projectTest.security.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class UserLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.info("[UserLoginFailureHandler onAuthenticationFailure] exception : {}", exception.getMessage());
		
		int errorCode = HttpStatus.BAD_REQUEST.value();
		// errorCode = 400
		setDefaultFailureUrl("/login?error=" + errorCode);
		// 인증 실패 로직 처리 
		super.onAuthenticationFailure(request, response, exception);
		
	}
}
