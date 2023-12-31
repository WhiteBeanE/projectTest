package com.bsm.projectTest.jwt.service.Impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsm.projectTest.jwt.domain.MemberLoginDto;
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
	public String login(MemberLoginDto memberDto) {
		
		// 1. Login ID/PW를 기반으로 Authentication 객체 생성
		// authentication는 인증 여부를 확인하는 authenticated 값이  false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDto.getMemberId(), memberDto.getPassword());
		log.info("[JwsServiceImpl login] authenticationToken : {}", authenticationToken);
		
		// 2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 매소드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메소드가 실행
		// authenticationManangerBuilder.getObject().authenticate() 메소드가 실행되면 AuthenticationManager의 구현체인 ProviderManager의 authenticate() 메소드가 실행
		// 해당 메소드에선 AuthenticaionProvider 인터페이스의 authenticate() 메소드를 실행하는데 해당 인터페이스에서 데이터베이스에 있는 이용자의 정보를 가져오는  UserDetailsService 인터페이스를 사용
		// CustomUserDetailsService는  UserDetailsService를 구현한 Class
		
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		log.info("[JwsServiceImpl login] authentication : {}", authentication);
		
		// MemberDto member = jwtDao.findMemberByMemberIdNoAuthentication(memberId);
		// 3. 인증 정보를 기반으로 JWT 생성
		//TokenDto tokenDto = jwtProvider.generateToken(authentication);
		// TokenDto tokenDto = jwtProvider.generateToken(member);
		String token = jwtProvider.createJwt(authentication);
		
		return token;
	}

	@Override
	public int jwtCheck(HttpServletRequest request) {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorization == null || !authorization.startsWith("Bearer ")) {
			return 201;
		} 
		String jwt = authorization.split(" ")[1];
		log.info("[JwsServiceImpl jwtCheck] jwt : {}", jwt);
		if(jwtProvider.vaildateToken(jwt)) {
			return 200;
		}
		return 201;
	}
	
}
