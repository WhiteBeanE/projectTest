package com.bsm.projectTest.webSocket.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bsm.projectTest.webSocket.dao.WebSocketDao;
import com.bsm.projectTest.webSocket.domain.ClinicDto;
import com.bsm.projectTest.webSocket.service.WebSocketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {
	
	private final WebSocketDao webSocketDao;
	
	@Override
	public List<ClinicDto> findClinicByStatus(String status) {
		return webSocketDao.selectClinicListByStatus(status);
	}

	@Override
	public String modifyClinicByPatient(String patient, ClinicDto clinic) {
		String msg = "";
		clinic.setPatient(patient);
		int result = webSocketDao.updateClinicByPatient(clinic);
		if(result > 0) {
			log.info("[modifyClinicByPatient] 상태 변환 성공");
			msg = "상태 변환 성공";
		}
		return msg;
	}

}
