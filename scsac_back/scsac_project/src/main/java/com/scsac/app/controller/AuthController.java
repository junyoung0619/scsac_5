package com.scsac.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.scsac.app.dto.LoginRequest;
import com.scsac.app.entity.UserEntity;
import com.scsac.app.entity.UserRole;
import com.scsac.app.repository.UserRepository;
import com.scsac.app.security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authManager;
	private final JwtTokenProvider tokenProvider;
	private final UserRepository userRepository;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest req){
		Authentication authentication = authManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword()));
		
		UserEntity user = userRepository.findById(request.getId())
							.orElseThrow(()-> new UsernameNotFoundException("사용자 없음"));
		
		String role = UserRole.transform(user.getAuthority(), user.getName()).name();
		
		String token = tokenProvider.generateToken(user.getId(), role);
		
		return ResponseEntity.ok().body("Bearer "+ token);
	}
	
	@PostMapping("/check")
	public ResponseEntity<?> check(@RequestBody LoginRequest request, HttpServletRequest req){
		
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getId(), request.getPassword()));
	    
		return ResponseEntity.ok("비밀번호 일치!");
	}
}


