package com.bsm.projectTest.webSocket.dao;

import java.util.List;

import com.bsm.projectTest.webSocket.domain.ClinicDto;

public interface WebSocketDao {

	List<ClinicDto> selectClinicListByStatus(String status);

	int updateClinicByPatient(ClinicDto clinic);

}
