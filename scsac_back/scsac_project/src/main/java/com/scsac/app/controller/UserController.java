package com.scsac.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService us;

	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable String id) {
		User user = us.findById(id);
		if (user != null) {
			user.setPassword("");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> insert(@RequestParam int num, @RequestParam int generation,
			@RequestParam String password) {
		int check = us.isExist(generation);
		if (check == 1) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		int r = us.insertUser(num, generation, password);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> me() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return detail(auth.getName());
	}

	@PutMapping("/user")
	public ResponseEntity<?> update(@RequestBody User user) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		String id = (String) auth.getPrincipal();

		if (!id.equals(user.getId())) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		int r = us.updateUser(user);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateauhthority(@RequestParam int generation) {

		int r = us.updateAuthority(generation);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/addAdmin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateAdmin(@RequestParam String id) {
		int r = us.addAdmin(id);
		if (r == 1) {
			return new ResponseEntity<Integer>(r, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
}
