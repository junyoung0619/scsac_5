package com.scsac.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scsac.app.dto.Opinion;
import com.scsac.app.dto.Problem;

public interface ProblemService {
	List<Problem> selectAll();
	public Page<Problem> selectPagedProblems(Pageable pageable);
	Problem selectById(int id);
	Page<Problem> searchPagedProblems(String searchCondition, String value, Pageable pageable);
	int insertProblem(Problem problem);
	int deleteProblem(int id);
	int updateProblem(Problem problem);
	int updateProblemRate(Problem problem);

}
