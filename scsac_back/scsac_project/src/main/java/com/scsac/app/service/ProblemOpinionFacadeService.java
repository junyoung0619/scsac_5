package com.scsac.app.service;

import com.scsac.app.dto.Opinion;
import com.scsac.app.dto.Problem;

public interface ProblemOpinionFacadeService {
	public int addProblemWithOpinion(Problem problem, Opinion opinion);
}
