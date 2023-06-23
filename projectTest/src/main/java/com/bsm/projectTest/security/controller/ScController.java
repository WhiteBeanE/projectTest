package com.bsm.projectTest.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScController {
	
	@GetMapping("/rn/rn-page")
	public String rnPage() {
		return "security/rn/rn-page";
	}
	
	@GetMapping("/dt/dt-page")
	public String dtPage() {
		return "security/dt/dt-page";
	}
}
