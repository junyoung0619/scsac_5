package com.scsac.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsac.app.dto.FeedbackCategory;
import com.scsac.app.service.FeedbackCategoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/feedbackCategory")
@RestController
@RequiredArgsConstructor
public class FeedbackCategoryController {
	
	private final FeedbackCategoryService fs;
	
	@GetMapping("/")
	public ResponseEntity<?> selectAll(){
		List<FeedbackCategory> fcs = fs.selectAll();
		if(fcs.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<FeedbackCategory>>(fcs,HttpStatus.OK);
		}
	}
}
