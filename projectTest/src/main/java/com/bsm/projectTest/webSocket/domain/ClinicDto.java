package com.bsm.projectTest.webSocket.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClinicDto {
	
	private String patient;
	private String medicalDate;
	private String status;
}
