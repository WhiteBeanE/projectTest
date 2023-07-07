package com.bsm.projectTest.jwt.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter{

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Token 확인
		// header에서  authorization꺼내기
		final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("[JwtFilter doFilterInternal] authorization : {}", authorization );
		
		// !ahthorization.startsWith("Bearer ") 얘는 포스트맨 쓸 때 팰요가 그다지 없네 authorization 종류별로 하는거라
		if(authorization == null || !authorization.startsWith("Bearer ")) {
			log.error("[JwtFilter doFilterInternal] authorization is Null or NOT startsWith 'Bearer '");
			filterChain.doFilter(request, response);
			return;
		}
		// Token 꺼내기
		final String token = authorization.split(" ")[1];
		
		// Token Expired되었는지 확인
		try {
			if(jwtProvider.isExpired(token)) {
				filterChain.doFilter(request, response);
				return;
			}
		} catch (io.jsonwebtoken.ExpiredJwtException e) {
			log.error("[JwtFilter doFilterInternal] authorization is Expired");
			return;
		}
		// 이러면 로그가 안뜨고 익셉션 걸려서 끝나버림
//		if(jwtUtil.isExpired(token)) {
//			log.error("[JwtFilter doFilterInternal] authorization is Expired");
//			filterChain.doFilter(request, response);
//			return;
//		}
		
		// userName Token에서 꺼내기
//		String userName = jwtProvider.getUsernameFromToken(token);
		String userName = jwtProvider.getUserName(token);
		log.info("[JwtFilter doFilterInternal] userName : {}", userName);
		
		// 권한 부여
//		UsernamePasswordAuthenticationToken authenticationToken = 
//				new UsernamePasswordAuthenticationToken(userName, new SimpleGrantedAuthority(jwtProvider.getRole(token)));
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority(jwtProvider.getRole(token))));
		log.info("[JwtFilter doFilterInternal] authenticationToken : {}", authenticationToken);
//		UsernamePasswordAuthenticationToken authenticationToken = 
//				new UsernamePasswordAuthenticationToken(userName, null, jwtUtil.getRole(token).name());
		// Detail 설정
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		log.info("[JwtFilter doFilterInternal] authenticationToken : {}", authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		log.info("[JwtFilter doFilterInternal] authenticationToken : {}", authenticationToken);
		filterChain.doFilter(request, response);
		
	}

}
