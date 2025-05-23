package com.scsac.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scsac.app.security.LoginRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final PasswordEncoder pe;
	private final AuthenticationManager authenticaionManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest req){
		UsernamePasswordAuthenticationToken token = 
			new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword());
		
		Authentication authResult = authenticaionManager.authenticate(token);
				
		SecurityContextHolder.getContext().setAuthentication(authResult);
		
		req.getSession(true);
		
		return ResponseEntity.ok("로그인 성공!");
	}
}


