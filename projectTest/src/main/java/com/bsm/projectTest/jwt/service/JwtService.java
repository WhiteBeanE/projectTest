package com.bsm.projectTest.jwt.service;

import com.bsm.projectTest.jwt.domain.MemberLoginDto;
import com.bsm.projectTest.jwt.domain.TokenDto;

public interface JwtService {
	
	public TokenDto login(MemberLoginDto memberDto);
}
