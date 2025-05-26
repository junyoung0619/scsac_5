package com.scsac.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsac.app.dto.Problem;
import com.scsac.app.service.OpinionService;
import com.scsac.app.service.ProblemService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/problem")
@RestController
@RequiredArgsConstructor
public class ProblemController {
	private final ProblemService ps;
	private final OpinionService os;
	
	@GetMapping("/")
	public ResponseEntity<?> selectAll(){
		List<Problem> problems = ps.selectAll();
		if(problems!=null) {
			return new ResponseEntity<List<Problem>>(problems,HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
}
