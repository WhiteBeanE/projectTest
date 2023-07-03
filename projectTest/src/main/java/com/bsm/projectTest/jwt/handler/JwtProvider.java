package com.bsm.projectTest.jwt.handler;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bsm.projectTest.jwt.domain.MemberDto;
import com.bsm.projectTest.jwt.domain.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider {
	private final Key key;
	
	public JwtProvider(@Value("${jwt.secret}") String secretKey) {
		log.info("[JwtProvider] secretKey : {}", secretKey);
		byte[] ketBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(ketBytes);
	}
	
	// 유저 정보를 가지고 Accesstoken, RefreshToken을 생성하는 메소드
	public TokenDto generateToken(Authentication authentication) {
		// 권한 가져오기
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		long now = (new Date().getTime());
		
		// Access Token 생성
		Date accessTokenExpiresIn = new Date(now + 86400000);
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim("auth", authorities)
				.setExpiration(accessTokenExpiresIn)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		// Refresh Token 생성
		String refreshToken = Jwts.builder()
				.setExpiration(new Date(now + 86400000))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		TokenDto tokenDto = new TokenDto("Bearer", accessToken, refreshToken);
		return tokenDto;
//		return TokenDto.builder()
//				.grantType("Bearer")
//				.accessToken(accessToken)
//				.refreshToken(refreshToken)
//				.build();
	}
	
//	public TokenDto generateToken(MemberDto member) {
//		// 권한 가져오기
//		String authorities = member.getRoles();
//		
//		long now = (new Date().getTime());
//		
//		// Access Token 생성
//		Date accessTokenExpiresIn = new Date(now + 86400000);
//		String accessToken = Jwts.builder()
//				.setSubject(member.getMemberId())
//				.claim("auth", authorities)
//				.setExpiration(accessTokenExpiresIn)
//				.signWith(key, SignatureAlgorithm.HS256)
//				.compact();
//		
//		// Refresh Token 생성
//		String refreshToken = Jwts.builder()
//				.setExpiration(new Date(now + 86400000))
//				.signWith(key, SignatureAlgorithm.HS256)
//				.compact();
//		
//		return TokenDto.builder()
//				.grantType("Bearer")
//				.accessToken(accessToken)
//				.refreshToken(refreshToken)
//				.build();
//	}
	
	// JWT 토근을 복호화하여 토큰에 들어있는 정보를 꺼내는 메소드
	public Authentication getAuthentication(String accessToken) {
		// 토큰 복호화
		Claims claims = parseClaims(accessToken);
		
		if(claims.get("auth") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		// 클레임에서 권한 정보 가져오기
		Collection<? extends GrantedAuthority> authoritis = 
				Arrays.stream(claims.get("auth").toString().split(","))
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		
		// UserDetails 객체를 만들어 Authentication 리턴
		UserDetails principal = new User(claims.getSubject(), "", authoritis);
		return new UsernamePasswordAuthenticationToken(principal, "", authoritis);
	}
	
	// 토근 정보를 검증하는 메소드
	public boolean vaildateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("[JwtProvider vaildateToken] Invaild JWT : {}", e);
		} catch (ExpiredJwtException e) {
			log.info("[JwtProvider vaildateToken] Expired JWT : {}", e);
		} catch (UnsupportedJwtException e) {
			log.info("[JwtProvider vaildateToken] Unsupported JWT : {}", e);
		} catch (IllegalArgumentException e) {
			log.info("[JwtProvider vaildateToken] JWT claims String is empty : {}", e);
		}
		return false;
	}
	
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
	
} // Class
