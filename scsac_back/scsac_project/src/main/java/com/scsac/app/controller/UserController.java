package com.scsac.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scsac.app.dto.User;
import com.scsac.app.service.UserService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService us;

	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable String id) {
		User user = us.findbyId(id);
		if (user != null) {
			user.setPassword("");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> insert(@AuthenticationPrincipal int authority, @RequestParam int num, @RequestParam int generation,
			@RequestParam String password) {
		int r = us.insertUser(num, generation, password);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/user")
	public ResponseEntity<?> update(@AuthenticationPrincipal String id,@RequestBody User user) {
		if(!id.equals(user.getId())) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		int r = us.updateUser(user);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateauhthority(@AuthenticationPrincipal int authority, @RequestParam int generation) {
		int r = us.updateAuthority(generation);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/add_admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateAdmin(@AuthenticationPrincipal int authority, @RequestParam String id) {
		User user = us.findbyId(id);
		user.setAuthority(1);
		int r = us.updateUser(user);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
