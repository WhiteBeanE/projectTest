package com.bsm.projectTest.jwt.handler;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
// 클라이언트 요청 시 JWT 인증을 하기 위해 설치하는 커스텀 필터로 UsernamePasswordAuthenticationFilter 이전에 실행
// JwtAuthenticationFilter를 통과하면 UsernamePasswordAuthenticationFilter 이후의 필터는 통과한 것으로 본다는 뜻
// Username + Password를 통한 인증을 Jwt를 통해 수행
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtProvider jwtProvider;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		log.info("[JwtAuthenticationFilter doFilter]");
		
		// 1. Request Header에서 JWT 추출
//		String token = resolveToken((HttpServletRequest) request);
		String token = (String)Arrays.stream(((HttpServletRequest) request).getCookies())
				.filter(c -> c.getName().equals("Token"))
				.findFirst().map(Cookie::getValue).orElse(null);
		
		log.info("[JwtAuthenticationFilter doFilter] token : {}", token);
		
		// 2. validateToken으로 토큰 유효성 검사
		if(token != null && jwtProvider.vaildateToken(token)) {
			// 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
			Authentication authentication = jwtProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
	
	// Request Header에서 토큰 정보 추출
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
