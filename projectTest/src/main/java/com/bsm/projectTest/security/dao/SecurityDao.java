package com.bsm.projectTest.security.dao;

import com.bsm.projectTest.security.domain.MemberDto;

public interface SecurityDao {

	MemberDto selectMemberByMemberId(String memberId);

}
