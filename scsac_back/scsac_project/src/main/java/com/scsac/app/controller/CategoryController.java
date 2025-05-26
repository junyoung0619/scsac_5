package com.scsac.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsac.app.dto.Category;
import com.scsac.app.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService cs;
	
	@GetMapping("/")
	public ResponseEntity<?> selectAll(){
		List<Category> categories = cs.selectAll();
		if(categories==null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Category>>(categories,HttpStatus.OK);
	}
}
