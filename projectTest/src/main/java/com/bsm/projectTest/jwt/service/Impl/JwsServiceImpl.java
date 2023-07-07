package com.bsm.projectTest.jwt.service.Impl;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.domain.MemberLoginDto;
import com.bsm.projectTest.jwt.domain.TokenDto;
import com.bsm.projectTest.jwt.handler.JwtProvider;
import com.bsm.projectTest.jwt.jwtDao.JwtDao;
import com.bsm.projectTest.jwt.service.JwtService;
import com.bsm.projectTest.security.dao.SecurityDao;
import com.bsm.projectTest.security.domain.UserDetailDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class JwsServiceImpl implements JwtService {
	
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtProvider jwtProvider;
	private final JwtDao jwtDao;
	private final SecurityDao securityDao;
	
//	@Override
//	public TokenDto login(MemberDto member) {
//		
//		UsernamePasswordAuthenticationToken authenticationToken = 
//				new UsernamePasswordAuthenticationToken(member.getMemberId(), member.getPassword(), member.getAuthorities());
//		log.info("[JwsServiceImpl login] authenticationToken : {}", authenticationToken);
//		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//		log.info("[JwsServiceImpl login] authentication : {}", authentication);
//		
//		TokenDto tokenDto = jwtProvider.generateToken(authentication);
//		log.info("[JwsServiceImpl login] tokenDto : {}", tokenDto);
//		
//		return tokenDto;
//	}
	
	@Override
//	public TokenDto login(MemberLoginDto memberDto) {
	public String login(MemberLoginDto memberDto) {
		log.info("[JwsServiceImpl login] memberDto : {}", memberDto);
		
		String memberId = memberDto.getMemberId();
		String password = memberDto.getPassword();
		
		// 1. Login ID/PW를 기반으로 Authentication 객체 생성
		// authentication는 인증 여부를 확인하는 authenticated 값이  false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
		// UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password, List.of(new SimpleGrantedAuthority("rn")));
		log.info("[JwsServiceImpl login] authenticationToken : {}", authenticationToken);
		// authenticationToken.setAuthenticated(true);
		// 인증을 안했으니 Authenticated이 false가 맞지 근데 어디서 Null이 뜬다는 거야
		
		// 2. 실제 검증(사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 매소드가 실행될 때 CustomUserDetailsService에서 만든 loadUserByUsername 메소드가 실행
		// authenticationManangerBuilder.getObject().authenticate() 메소드가 실행되면 AuthenticationManager의 구현체인 ProviderManager의 authenticate() 메소드가 실행
		// 해당 메소드에선 AuthenticaionProvider 인터페이스의 authenticate() 메소드를 실행하는데 해당 인터페이스에서 데이터베이스에 있는 이용자의 정보를 가져오는  UserDetailsService 인터페이스를 사용
		// CustomUserDetailsService는  UserDetailsService를 구현한 Class
		
		// 문제점 확인 UserDetailsService를 구현안 객체가 2개라서  생성이 안되고 null로 들어가고 있음
		// 세션을 통한 인증 구현 시 UserDetailsService를 구현한 MemberDetailsServiceImpl 객체가 존재함
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		log.info("[JwsServiceImpl login] authentication : {}", authentication);
		
		// MemberDto member = jwtDao.findMemberByMemberIdNoAuthentication(memberId);
		// 3. 인증 정보를 기반으로 JWT 생성
		//TokenDto tokenDto = jwtProvider.generateToken(authentication);
		// TokenDto tokenDto = jwtProvider.generateToken(member);
//		String token = jwtProvider.generateToken(authentication);
		String token = jwtProvider.createJwt(authentication);
		
		return token;
	}

	@Override
	public UserDetails loadUserByUsername(String memberId) {
		com.bsm.projectTest.security.domain.MemberDto member =  securityDao.selectMemberByMemberId(memberId);
		if(member != null) {
			UserDetailDto userDetailDto = new UserDetailDto(member, getRoles(member));
			log.info("[loadUserByUsername] userDetailsDto : {}", userDetailDto);
			
			return userDetailDto;
		}
		throw new BadCredentialsException("No such id or wrong password");
	}
	
	private Collection<GrantedAuthority> getRoles(com.bsm.projectTest.security.domain.MemberDto memberDto) {
		Collection<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + memberDto.getMemberRole()));
		
		return roles;
	}
}
