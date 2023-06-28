package com.bsm.projectTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ProjectTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTestApplication.class, args);
	}

}
