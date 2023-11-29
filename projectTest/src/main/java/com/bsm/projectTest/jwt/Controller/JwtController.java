package com.bsm.projectTest.jwt.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<Void> login(@RequestBody MemberLoginDto memberDto) {
		log.info("[JwtController login] memberDto : {}", memberDto);
		log.info("IntelliJ Git Test");
		String jwt = jwtService.login(memberDto);
		log.info("[JwtController login] jwt : {}", jwt);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwt);
		return ResponseEntity
			.status(HttpStatus.OK)
			.headers(headers)
			.build();
	}
	@GetMapping("/user")
	public ResponseEntity<String> user(HttpServletRequest request){
		log.info("[JwtController user] Start");
		int status = jwtService.jwtCheck(request);
		HttpHeaders headers = new HttpHeaders();
		log.info("[JwtController user] status : {}", status);
		return ResponseEntity.status(status).headers(headers).build();
		
	}
}
