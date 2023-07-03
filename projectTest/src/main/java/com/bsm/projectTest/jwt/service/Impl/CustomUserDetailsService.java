package com.bsm.projectTest.jwt.service.Impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.domain.MemberLoginDto;
import com.bsm.projectTest.jwt.jwtDao.JwtDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final JwtDao jwtDao;
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		log.info("[CustomUserDetailsService loadUserByUsername] memberId : {}", memberId);
		MemberLoginDto member =  jwtDao.findMemberByMemberIdNoAuthentication(memberId);
		
		if (member != null) {
					
			MemberDto memberDto = createUserDetails(member);
			log.info("[CustomUserDetailsService loadUserByUsername] memberDto : {}", memberDto);
			
			return memberDto;
		}
		
		return null;
	}
	
	// 해당하는 User의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
	private MemberDto createUserDetails(MemberLoginDto member) {
		log.info("[CustomUserDetailsService createUserDetails] memberDto : {}", member);
		Collection<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + member.getRoles()));
		MemberDto memberDto = new MemberDto();
		memberDto.setMemberId(member.getMemberId());
		memberDto.setPassword(member.getPassword());
		memberDto.setRoles(roles);
		return memberDto;
				
	}

}
