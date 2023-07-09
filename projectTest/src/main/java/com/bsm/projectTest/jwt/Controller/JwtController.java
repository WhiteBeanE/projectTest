package com.bsm.projectTest.jwt.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsm.projectTest.jwt.domain.MemberLoginDto;
import com.bsm.projectTest.jwt.service.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/jwt")
public class JwtController {
	
	private final JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody MemberLoginDto memberDto) {
		log.info("[JwtController login] memberDto : {}", memberDto);
		//TokenDto tokenDto = jwtService.login(memberDto);
		String jwt = jwtService.login(memberDto);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwt);
		log.debug("[login] jwt: {}", jwt);
		return ResponseEntity
			.status(HttpStatus.OK)
			.headers(headers)
			.build();
//		return tokenDto;
	}
	
}
