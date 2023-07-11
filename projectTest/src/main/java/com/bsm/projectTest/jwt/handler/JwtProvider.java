package com.bsm.projectTest.jwt.handler;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
	private Long expiredMs = 1000 * 60 * 60 * 24L;
	
	public JwtProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] ketBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(ketBytes);
	}
	
	public boolean isExpired(String token) {
		return parseClaims(token).getExpiration().before(new Date());
	}
	
	public String getUserName(String token) {
        return String.valueOf(parseClaims(token).get("userName"));
	}
	
	public String getRole(String token) {
		return String.valueOf(parseClaims(token).get("role"));
	}
	
	public String createJwt(Authentication authentication) {
		
		// 권한 가져오기
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		String userName = authentication.getName();
		Claims claims = Jwts.claims();
		claims.put("userName",userName);
		claims.put("role", authorities);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	// 유저 정보를 가지고 Accesstoken, RefreshToken을 생성하는 메소드
	public String generateToken(Authentication authentication) {
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
//		String refreshToken = Jwts.builder()
//				.setExpiration(new Date(now + 86400000))
//				.signWith(key, SignatureAlgorithm.HS256)
//				.compact();
//		TokenDto tokenDto = new TokenDto("Bearer", accessToken, refreshToken);
		return accessToken;
//		return tokenDto;
//		return TokenDto.builder()
//				.grantType("Bearer")
//				.accessToken(accessToken)
//				.refreshToken(refreshToken)
//				.build();
	}
	
	
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
		} catch (SecurityException | MalformedJwtException e) {
			log.error("[JwtProvider vaildateToken] Invaild JWT : {}", e);
		} catch (ExpiredJwtException e) {
			log.error("[JwtProvider vaildateToken] Expired JWT : {}", e);
		} catch (UnsupportedJwtException e) {
			log.error("[JwtProvider vaildateToken] Unsupported JWT : {}", e);
		} catch (IllegalArgumentException e) {
			log.error("[JwtProvider vaildateToken] JWT claims String is empty : {}", e);
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
