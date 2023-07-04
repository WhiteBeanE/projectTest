package com.bsm.projectTest.jwt.jwtDao.Impl;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.domain.MemberLoginDto;
import com.bsm.projectTest.jwt.jwtDao.JwtDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class JwtDaoImpl implements JwtDao {
	
	private final SqlSession session;
	
	@Override
	public Optional<MemberDto> findMemberByMemberId(String memberId) {
		log.info("[JwtDaoImpl findMemberByMemberId] memberId : {}", memberId);
		return session.selectOne("findMemberByMemberId", memberId);
	}

	@Override
	public MemberLoginDto findMemberByMemberIdNoAuthentication(String memberId) {
		log.info("[JwtDaoImpl findMemberByMemberIdNoAuthentication] memberId : {}", memberId);
		return session.selectOne("findMemberByMemberId", memberId);
	}

	@Override
	public MemberDto selectUserByUserName(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

}
