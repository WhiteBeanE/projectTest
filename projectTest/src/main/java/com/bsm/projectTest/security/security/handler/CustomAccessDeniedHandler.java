package com.bsm.projectTest.security.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("[CustomAccessDeniedHandler handle] accessDeniedException : {}", accessDeniedException.getMessage());
		
		String redirectUrl = "/not-authorized";
		// Ajax 요청일 시 페이지 이동 하지 않고 에러코드와 msg
		if(isAjaxRequest(request)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
		}else {
            response.sendRedirect(redirectUrl);
        }
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

}
