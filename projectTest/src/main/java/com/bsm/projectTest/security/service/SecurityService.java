package com.bsm.projectTest.security.service;

import com.bsm.projectTest.security.domain.MemberDto;

public interface SecurityService {

	MemberDto findMemberBymemberId(String memberId);
	
}
