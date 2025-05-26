package com.scsac.app.service;

import java.util.List;

import com.scsac.app.dto.Opinion;

public interface OpinionService {
	List<Opinion> findByProblemId(int problemId);
}
