package com.bsm.projectTest.security.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsm.projectTest.security.dao.SecurityDao;
import com.bsm.projectTest.security.domain.MemberDto;
import com.bsm.projectTest.security.service.SecurityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService{

	private final SecurityDao securityDao;
	
	@Override
	public MemberDto findMemberBymemberId(String memberId) {
		log.info("[findMemberBymemberId] memberId : {}", memberId);
		return securityDao.selectMemberByMemberId(memberId);
	}

}
