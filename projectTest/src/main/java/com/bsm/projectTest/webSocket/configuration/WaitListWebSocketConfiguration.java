package com.bsm.projectTest.webSocket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.bsm.projectTest.webSocket.handler.WaitListWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSocket
@Slf4j
@RequiredArgsConstructor
public class WaitListWebSocketConfiguration implements WebSocketConfigurer {
	
	private final WaitListWebSocketHandler waitListWebSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		log.info("registerWebSocketHandlers");
		
		registry.addHandler(waitListWebSocketHandler, "/wait-ws");

	}

}
