package com.bsm.projectTest.jwt.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberLoginDto {
	
	private String memberId;
	private String password;
}
