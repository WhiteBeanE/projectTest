package com.bsm.projectTest.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.domain.MemberLoginDto;
import com.bsm.projectTest.jwt.domain.TokenDto;

public interface JwtService {
	
	public TokenDto login(MemberDto member);
	public TokenDto login(MemberLoginDto memberDto);
	public UserDetails loadUserByUsername(String memberId);
}
