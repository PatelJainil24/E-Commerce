package com.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.auth.dto.AuthenticationRequest;
import com.app.auth.dto.AuthenticationResponse;
import com.app.auth.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/authenticate")
@Slf4j
public class AuthenticationController {

	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping
	public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request) {
		
		System.out.println("Auth controller call...");
		log.info("Auth controller login:");
		
		return authenticationService.authenticate(request);
	}
}
