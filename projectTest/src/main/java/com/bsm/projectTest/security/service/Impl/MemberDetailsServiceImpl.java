package com.bsm.projectTest.security.service.Impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bsm.projectTest.security.domain.MemberDto;
import com.bsm.projectTest.security.domain.UserDetailDto;
import com.bsm.projectTest.security.service.SecurityService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

	private final SecurityService securityService;
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		log.info("[loadUserByUsername] memberId : {}", memberId);
		
		MemberDto memberDto = securityService.findMemberBymemberId(memberId);
		
		if(memberDto != null) {
			
			UserDetailDto userDetailDto = new UserDetailDto(memberDto, getRoles(memberDto));
			log.info("[loadUserByUsername] userDetailsDto : {}", userDetailDto);
			
			return userDetailDto;
			
		}
		
		throw new BadCredentialsException("No such id or wrong password");
	}
	
	private Collection<GrantedAuthority> getRoles(MemberDto memberDto) {
		Collection<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + memberDto.getMemberRole()));
		
		return roles;
	}

}
