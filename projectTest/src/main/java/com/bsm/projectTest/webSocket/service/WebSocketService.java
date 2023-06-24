package com.bsm.projectTest.webSocket.service;

import java.util.List;

import com.bsm.projectTest.webSocket.domain.ClinicDto;

public interface WebSocketService {

	List<ClinicDto> findClinicByStatus(String status);

	String modifyClinicByPatient(String patient, ClinicDto clinic);

}
