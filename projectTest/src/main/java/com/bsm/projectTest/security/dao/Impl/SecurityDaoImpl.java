package com.bsm.projectTest.security.dao.Impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.bsm.projectTest.security.dao.SecurityDao;
import com.bsm.projectTest.security.domain.MemberDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SecurityDaoImpl implements SecurityDao {
	
	private final SqlSession session;
	
	@Override
	public MemberDto selectMemberByMemberId(String memberId) {
		log.info("[selectMemberByMemberId] memberId : {}", memberId);
		
		MemberDto member = session.selectOne("selectMemberByMemberId", memberId);
		log.info("[selectMemberByMemberId] member : {}", member);
		
		return member;
	}

}
