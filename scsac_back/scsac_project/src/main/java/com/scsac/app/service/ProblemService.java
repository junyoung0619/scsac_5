package com.scsac.app.service;

import java.util.List;

import com.scsac.app.dto.Problem;

public interface ProblemService {
	List<Problem> selectAll();
	List<Problem> selectBySearchcondition(String condition);
	
}
