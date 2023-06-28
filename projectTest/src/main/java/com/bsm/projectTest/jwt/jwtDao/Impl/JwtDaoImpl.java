package com.bsm.projectTest.jwt.jwtDao.Impl;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.jwtDao.JwtDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class JwtDaoImpl implements JwtDao {
	
	private final SqlSession session;
	
	@Override
	public Optional<MemberDto> findMemberByMemberId(String username) {
		log.info("[findMemberByMemberId] username : {}", username);
		return session.selectOne("findMemberByMemberId", username);
	}

}
