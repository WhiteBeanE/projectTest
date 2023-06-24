package com.bsm.projectTest.webSocket.dao.Impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bsm.projectTest.webSocket.dao.WebSocketDao;
import com.bsm.projectTest.webSocket.domain.ClinicDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WebSocketDaoImpl implements WebSocketDao {
	
	private final SqlSession session;
	
	@Override
	public List<ClinicDto> selectClinicListByStatus(String status) {
		List<ClinicDto> clinicList = session.selectList("selectClinicListByStatus", status);
		log.info("[selectClinicListByStatus] clinicList : {}", clinicList);
		return clinicList;
	}

	@Override
	public int updateClinicByPatient(ClinicDto clinic) {
		
		return session.update("updateClinicByPatient", clinic);
	}

}
