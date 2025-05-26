package com.scsac.app.service;

import java.util.List;
import java.util.Optional;

import com.scsac.app.dto.Opinion;

public interface OpinionService {
	List<Opinion> findByProblemId(int problemId);
	Optional<Integer> getProblemIdByOpinionId(int opinionId);	
	int insertOpinion(Opinion opinion);
	int updateOpinion(Opinion opinion);
	int deleteOpinion(int id);
}
