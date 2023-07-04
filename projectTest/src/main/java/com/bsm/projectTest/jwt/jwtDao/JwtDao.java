package com.bsm.projectTest.jwt.jwtDao;

import java.util.Optional;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.domain.MemberLoginDto;

public interface JwtDao {
	Optional<MemberDto> findMemberByMemberId(String username);
	MemberLoginDto findMemberByMemberIdNoAuthentication(String username);
	MemberDto selectUserByUserName(String memberId);
}
