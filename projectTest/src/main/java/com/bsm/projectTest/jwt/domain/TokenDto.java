package com.bsm.projectTest.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
// 클라이언트에 토큰을 보내기 위한 DTO
public class TokenDto {
	
	// grantType은 JWT 대한 인증 타입
    private String grantType;
    private String accessToken;
    private String refreshToken;
    
}
