package com.bsm.projectTest.jwt.service.Impl;

import java.util.Collection;
import java.util.Collections;

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
		
		// return된 UserDetails 객체는 Spring Security가 사용자를 인증(authenticate)하고 권한 부여(authorize)하는 데 사용
		UserDetails user = jwtDao.findMemberByMemberId(memberId)
				.map(this::createUserDetails)
				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
		log.info("[CustomUserDetailsService loadUserByUsername] user : {}", user);
		return user;
//		return  jwtDao.findMemberByMemberId(memberId)
//				.map(this::createUserDetails)
//				.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
	}
	
    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(MemberLoginDto member) {
    	Collection<GrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRoles()));
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .roles(roles)
                .build();
    }

}
