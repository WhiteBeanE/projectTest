package com.bsm.projectTest.jwt.service.Impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.jwtDao.JwtDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final JwtDao jwtDao;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("[CustomUserDetailsService loadUserByUsername] username : {}", username);
		return jwtDao.findMemberByMemberId(username)
				.map(this::createUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다"));
				
	}
	
	// 해당하는 User의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
	private UserDetails createUserDetails(MemberDto memberDto) {
		log.info("[CustomUserDetailsService createUserDetails] memberDto : {}", memberDto);
		
		return User.builder()
				.username(memberDto.getUsername())
				.password(passwordEncoder.encode(memberDto.getPassword()))
				.roles(memberDto.getRoles().toArray(new String[0]))
				.build();
	}

}
