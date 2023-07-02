package com.bsm.projectTest.jwt.jwtDao;

import java.util.Optional;

import com.bsm.projectTest.jwt.domain.MemberDto;

public interface JwtDao {
	Optional<MemberDto> findMemberByMemberId(String username);
	MemberDto findMemberByMemberIdNoAuthentication(String username);
}
