package com.app.user.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUtilImpl implements JwtUtil{@Override
	
	
	public String generateToken(UserDetails userDetails) {
	
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("client", "applicaitonjava");
		claims.put("roles", userDetails.getAuthorities());
	
	
		return Jwts.builder().setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *15))
				.signWith(SignatureAlgorithm.HS256, "secret")
				.compact();
	}

	public Claims extractClaims(String token) {
		
		Claims claims = Jwts.parser()
			.setSigningKey("secret")
			.parseClaimsJws(token)
			.getBody();
		return claims;
	}

	@Override
	public String extractUsername(String token) {
		
		Claims claims = extractClaims(token);
		
		return claims.getSubject();
	}

	@Override
	public Date extractExpiration(String token) {
		
		Claims claims = extractClaims(token);
		
		return claims.getExpiration();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiredDate = extractExpiration(token);
		return expiredDate.before(new Date());
	}

	@Override
	public Boolean valicationToken(UserDetails userDetails, String token) {
		
		String username = extractUsername(token);
		
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	
	
}
