package com.scsac.app.controller;

import java.net.http.HttpRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class indexController {

	@GetMapping("/")
	public ResponseEntity<?> index(HttpServletRequest request){
		return ResponseEntity.ok().build();
	}
}
