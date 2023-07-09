package com.bsm.projectTest.jwt.service;

import com.bsm.projectTest.jwt.domain.MemberLoginDto;

public interface JwtService {
	
	public String login(MemberLoginDto memberDto);

}
