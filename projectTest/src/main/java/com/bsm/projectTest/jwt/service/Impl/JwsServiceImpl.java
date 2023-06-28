package com.bsm.projectTest.jwt.service.Impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsm.projectTest.jwt.domain.MemberLoginDto;
import com.bsm.projectTest.jwt.domain.TokenDto;
import com.bsm.projectTest.jwt.handler.JwtProvider;
import com.bsm.projectTest.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class JwsServiceImpl implements JwtService {
	
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtProvider jwtProvider;
	
	@Override
	public TokenDto login(MemberLoginDto memberDto) {
		log.info("[JwsServiceImpl login] memberDto : {}", memberDto);
		
		String memberId = memberDto.getMemberId();
		String password = memberDto.getPassword();
		
		// 1. Login ID/PW를 기반으로 Authentication 객체 생성
		// authentication는 인증 여부를 확인하는 authenticated 값이  false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
		log.info("[JwsServiceImpl login] authenticationToken : {}", authenticationToken);
		//authenticationToken.setAuthenticated(true);
		
		
		// 2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 매소드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메소드가 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		// 3. 인증 정보를 기반으로 JWT 생성
		TokenDto tokenDto = jwtProvider.generateToken(authentication);
		
		return tokenDto;
	}
}
