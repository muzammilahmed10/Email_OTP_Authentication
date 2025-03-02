package com.web.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.web.demo.services.UserService;

@Controller
@RequestMapping("/api")
public class UserController {
	
	UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("un") String username,  @RequestParam("up") String password) {
		boolean result=userService.loginService(username, password);
		if(result) {
		return "verifyotp";
	 }
		return "invalid";
	}
	
	@PostMapping("/verify")
	public String verifyotp(@RequestParam("otp") String otp) {
		String msg = userService.verify(otp);
		
		if(msg.equals("success")) {
			return "dashboard";
		}
		else {
			return "otpexpired";
		}
	}
}
