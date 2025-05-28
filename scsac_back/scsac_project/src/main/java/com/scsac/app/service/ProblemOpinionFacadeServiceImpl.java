package com.scsac.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scsac.app.dto.Opinion;

import com.scsac.app.dto.Problem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemOpinionFacadeServiceImpl implements ProblemOpinionFacadeService {

	private final ProblemService ps;
	private final OpinionService os;

	@Override
	@Transactional
	public int addProblemWithOpinion(Problem problem, Opinion opinion) {
		int r1 = ps.insertProblem(problem);
		opinion.setProblemId(problem.getId());
		int r2 = os.insertOpinion(opinion);
		return r1 * r2;
	}

}
