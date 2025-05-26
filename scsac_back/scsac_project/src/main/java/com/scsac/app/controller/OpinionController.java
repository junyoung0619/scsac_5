package com.scsac.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsac.app.dto.Opinion;
import com.scsac.app.service.OpinionService;
import com.scsac.app.service.ProblemService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/opinion")
@RestController
@RequiredArgsConstructor
public class OpinionController {

	private final OpinionService os;
	private final ProblemService ps;

	@GetMapping("/{id}")
	public ResponseEntity<?> selectbyProblemId(@PathVariable int id) {
		List<Opinion> opinions = os.findByProblemId(id);

		if (opinions == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<List<Opinion>>(opinions, HttpStatus.OK);
		}
	}

	@PostMapping("/")
	public ResponseEntity<?> insertOpinion(@RequestBody Opinion opinion) {
		int r = os.insertOpinion(opinion);
		if (r == 1) {
			ps.updateProblemRate(ps.selectById(opinion.getProblemId()));
			return new ResponseEntity<Opinion>(opinion, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/")
	public ResponseEntity<?> updateOpinion(@RequestBody Opinion opinion) {
		int r = os.updateOpinion(opinion);
		if (r == 1) {
			ps.updateProblemRate(ps.selectById(opinion.getProblemId()));
			return new ResponseEntity<Opinion>(opinion, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOpinion(@PathVariable int id) {
		
		Optional<Integer> optional = os.getProblemIdByOpinionId(id);
		if (optional.isEmpty()) {
		    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		int problemId = optional.get();
		int r = os.deleteOpinion(id);
		if (r == 1) {
			List<Opinion> opinions = os.findByProblemId(problemId);
			if (opinions == null) {
				ps.deleteProblem(problemId);
			} else {
				ps.updateProblemRate(ps.selectById(problemId));
			}
			return new ResponseEntity<Integer>(1, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

}
