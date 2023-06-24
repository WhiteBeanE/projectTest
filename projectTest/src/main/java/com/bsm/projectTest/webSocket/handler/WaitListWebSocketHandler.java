package com.bsm.projectTest.webSocket.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WaitListWebSocketHandler extends TextWebSocketHandler {
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("[afterConnectionEstablished] 열림");
	}
	
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String payload = (String)message.getPayload();
		// 대기자 삭제만 동작하도록 설정
		log.info("[handleMessage] payload : {}", payload);
//		ClinicDto clinicDto = objectMapper.readValue(payload, new TypeReference<ClinicDto>() {});
		//session.sendMessage((WebSocketMessage<?>) message.getPayload());
		//session.sendMessage(new TextMessage(objectMapper.writeValueAsString(payload)));
//		session.sendMessage(new TextMessage(payload));
		session.sendMessage(message);
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("[afterConnectionEstablished] 닫힘");
	}
	
}
