package com.bsm.projectTest.webSocket.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WaitListWebSocketInterceptor extends HttpSessionHandshakeInterceptor {
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		log.info("beforeHandshake");
		ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
		HttpServletRequest httpServletRequest = serverHttpRequest.getServletRequest();
		HttpSession httpSession = httpServletRequest.getSession();
		attributes.put("memberId", httpSession.getAttribute("memberId"));
		
		return true;
	}
}
