package com.app.user.jwt;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;



public interface JwtUtil {

	String generateToken(UserDetails userDetails);
	
	String extractUsername(String token);
	
	Date extractExpiration(String token);
	
	Boolean valicationToken(UserDetails userDetails,String token);
	
}
