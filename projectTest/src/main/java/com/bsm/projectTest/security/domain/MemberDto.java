package com.bsm.projectTest.security.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	private String memberId;
	private String password;
	private String memberRole;
	
}
