package com.bsm.projectTest.jwt.jwtDao;

import java.util.Optional;

import com.bsm.projectTest.jwt.domain.MemberLoginDto;

public interface JwtDao {
	Optional<MemberLoginDto> findMemberByMemberId(String username);
}
