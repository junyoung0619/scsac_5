package com.scsac.app.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.scsac.app.dto.Opinion;
import com.scsac.app.dto.Problem;
import com.scsac.app.service.OpinionService;
import com.scsac.app.service.ProblemOpinionFacadeService;
import com.scsac.app.service.ProblemService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/problem")
@RestController
@RequiredArgsConstructor
public class ProblemController {

	private final ProblemService ps;
	private final OpinionService os;
	private final ProblemOpinionFacadeService pos;

	@GetMapping("")
	public ResponseEntity<Page<Problem>> getProblems(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
	    Page<Problem> problems = ps.selectPagedProblems(PageRequest.of(page, size));
	    return ResponseEntity.ok(problems);
	}
	
	
//	@GetMapping("/")
//	public ResponseEntity<?> selectAll() {
//		System.out.println("문제목록");
//		List<Problem> problems = ps.selectAll();
//		if (problems != null) {
//			return new ResponseEntity<List<Problem>>(problems, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//		}
//	}

	@GetMapping("/{id}")
	public ResponseEntity<?> selectById(@PathVariable int id) {
		Problem problem = ps.selectById(id);
		if (problem != null) {
			return new ResponseEntity<Problem>(problem, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchProblems(
	        @RequestParam String searchCondition,
	        @RequestParam String value,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
	    Page<Problem> problems = ps.searchPagedProblems(searchCondition, value, pageable);

	    if (problems.hasContent()) {
	        return new ResponseEntity<>(problems, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	}

	@PostMapping("/")
	public ResponseEntity<?> insertProblem(@RequestBody Problem problem) {
		if (problem.getOpinions() == null || problem.getOpinions().isEmpty()) {
		    return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		Opinion opinion = problem.getOpinions().get(0);
		opinion.setProblemId(problem.getId());
		opinion.setRate((int)problem.getRate());
		System.out.println(opinion);
		int r = pos.addProblemWithOpinion(problem, opinion);

		if (r == 1) {
			return new ResponseEntity<Problem>(problem, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/")
	public ResponseEntity<?> updateProblem(@RequestBody Problem problem) {
		int r = ps.updateProblem(problem);
		if (r == 1) {
			return new ResponseEntity<Problem>(problem, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

	}

}
