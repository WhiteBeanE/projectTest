package com.bsm.projectTest.security.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.domain.TokenDto;
import com.bsm.projectTest.jwt.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@RequiredArgsConstructor
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private final JwtService jwtService;
	private final ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//Security가 요청을 가로채기 전 request정보를 가지고 있는 객체
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		//login페이지로 직접 접속한 경우 redirectUrl의 default값 설정
		String redirectUrl = "/";
		//CustomUserDetailsService loadUserByUsername에서 인증성공한 member의 정보를 가져옴
		MemberDto memberInfo = (MemberDto) authentication.getPrincipal();
		log.info("[UserLoginSuccessHandler onAuthenticationSuccess] memberInfo : {}", memberInfo);
		
		//권한인증 실패로 인한 로그인인 경우
		if (savedRequest != null) {
			//Security가 요청을 가로채기 전 url로 redirect
			redirectUrl = savedRequest.getRedirectUrl();
			//메모리 누수 방지를 위한 session clearing(현재 로직에서는 session 객체를 사용할 필요가없어 사용하지 않았으나, 필요하면 사용 후 제거)
			requestCache.removeRequest(request, response);
		}
		//인증성공한 member의 정보를 가지고 토큰 생성 후 반환
//		TokenDto tokenDto = jwtService.login(memberInfo);
//		
//		response.setHeader("Authorization", "Bearer " + tokenDto);
//		
//		log.info("[UserLoginSuccessHandler onAuthenticationSuccess] tokenDto : {}", tokenDto);
//		response.setHeader("Authorization", tokenDto.toString());
		
//		// JWT 쿠키 저장(쿠키 명 : token)
//		Cookie cookie = new Cookie("Token", "Bearer " + tokenDto.getAccessToken());
//		cookie.setPath("/");
//		cookie.setMaxAge(60 * 60 * 24 * 1); // 유효기간 1일
//		// httoOnly 옵션을 추가해 서버만 쿠키에 접근할 수 있게 설정
//		cookie.setHttpOnly(true);
//		response.addCookie(cookie);
		
//		// 토큰을 JSON 형식으로 변환
//        String tokenJson = objectMapper.writeValueAsString(tokenDto);
//
//        // 응답 데이터 설정
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(tokenJson);
        
//		HttpSession session = request.getSession();
//		session.setAttribute("member", memberInfo.getMemberDto());
//		session.setAttribute("memberId", memberInfo.getMemberDto().getMemberId());
//		session.setAttribute("memberUid", memberInfo.getMemberDto().getMemberUid());
//		session.setAttribute("memberCompanyCode", memberInfo.getMemberDto().getCompanyCode());
//		log.info("[onAuthenticationSuccess] member : {}", memberInfo.getMemberDto());
		response.sendRedirect(redirectUrl);
		
	}

}