package com.bsm.projectTest.webSocket.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bsm.projectTest.webSocket.domain.ClinicDto;
import com.bsm.projectTest.webSocket.service.WebSocketService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebSocketController {
	
	private final WebSocketService webSocketService;
	
	@GetMapping("/wait-list")
	public String waitListPage() {
		return "webSocket/waitlist";
	}
	
	@GetMapping("/clinics/{status}")
	@ResponseBody
	public List<ClinicDto> ClinicByStatus(@PathVariable String status){
		log.info("[findClinicByStatus] status : {}", status);
		
		return webSocketService.findClinicByStatus(status);
	}
	
	@PutMapping("/clinics/{patient}")
	@ResponseBody
	public String clinicModifyByPatient(@PathVariable String patient, ClinicDto clinic) {
		log.info("[clinicModifyByPatient] patient : {}     clinic : {}",patient, clinic);
		
		return webSocketService.modifyClinicByPatient(patient, clinic);
	}
}
