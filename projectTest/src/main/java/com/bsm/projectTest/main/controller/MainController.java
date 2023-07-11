package com.bsm.projectTest.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	
	@GetMapping("")
	public String main() {
		log.info("main");
		return "main";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping("/not-authorized")
	public String notAuthorizedPage() {
		return "/security/not-authorized";
	}
	
}
