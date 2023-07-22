package com.bsm.projectTest.jwt.service;

import javax.servlet.http.HttpServletRequest;

import com.bsm.projectTest.jwt.domain.MemberLoginDto;

public interface JwtService {
	
	public String login(MemberLoginDto memberDto);

	public int jwtCheck(HttpServletRequest request);
}
