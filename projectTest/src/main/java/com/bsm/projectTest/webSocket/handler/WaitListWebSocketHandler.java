package com.bsm.projectTest.webSocket.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class WaitListWebSocketHandler extends TextWebSocketHandler {
	
	//Session 관리를 위한 Map
	private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
	private final List<WebSocketSession> webSocketSession;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("[afterConnectionEstablished] 열림");
		
		webSocketSession.add(session);
		
//		String memberId = (String) session.getAttributes().get("memberId");
//		log.info("afterConnectionEstablished memberId : " +  memberId);
//		if (memberId != null) {
//			sessionMap.put(memberId, session);
//		}
//		
//		sessionMap.forEach((key, value)->{
//			log.info("session map>> id : {} session : {}", key, value);
//		});
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
		
		for(WebSocketSession webSession : webSocketSession) {
			webSession.sendMessage(message);
		}
		
			// sessionMap.get(memberId)를 통해 memberId에 해당하는 웹소켓 세션(WebSocketSession)을 가져옴
//			WebSocketSession receiverSession = sessionMap.get("rn");
//			// 세션을 찾았다면, 해당 세션으로 데이터를 전송
//			if (receiverSession != null) {
//				try {
//					// trades 리스트를 JSON 문자열로 변환하고, 해당 문자열을 웹소켓 세션으로 전송
//					// objectMapper.writeValueAsString(trades)는 trades 리스트를 JSON 문자열로 변환하는 역할을 수행
//					receiverSession.sendMessage(message);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("[afterConnectionClosed] 닫힘");
		webSocketSession.remove(session);
	}
	
}
