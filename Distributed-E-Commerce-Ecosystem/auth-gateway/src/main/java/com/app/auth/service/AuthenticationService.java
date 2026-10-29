package com.app.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.auth.dto.AuthenticationRequest;
import com.app.auth.dto.AuthenticationResponse;
import com.app.user.jwt.JwtUtil;


@Service
public class AuthenticationService {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		System.out.println("Username: " + request.getUsername());
		System.out.println("Password: " + request.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		
		// Password check : 
		if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
			throw new IllegalArgumentException("Bad Credentials");
		}
		
		// JWT Token Generation: 
		
		String token = jwtUtil.generateToken(userDetails);
		return new AuthenticationResponse(token);
	}

}
