package com.scsac.app.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	private final Key key;
	private final long EXPIRATION_TIME;
	
	public JwtTokenProvider(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.expiration}") long expirationTime
	) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.EXPIRATION_TIME = expirationTime;
	}
	
	public String generateToken(String username, String role) {
		return Jwts.builder()
				.setSubject(username)
				.claim("role", "ROLE"+role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
				.signWith(key)
				.compact();
	}
	
	public String getUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build()
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
	
	public String getRole(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
							.parseClaimsJws(token)
							.getBody();
		
		return claims.get("role", String.class);
	}
}
