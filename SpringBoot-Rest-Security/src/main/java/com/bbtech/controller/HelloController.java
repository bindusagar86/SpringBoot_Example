package com.bbtech.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bbtech.com")
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
	
	@GetMapping("/admin/hello")
	public String adminHello() {
//		String user=auth.
		return "Hello Admin";
	}
}
