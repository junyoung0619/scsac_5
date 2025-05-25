package com.scsac.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scsac.app.dto.Problem;
import com.scsac.app.entity.ProblemEntity;
import com.scsac.app.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
	
	private final ProblemRepository pr; 
	
	@Override
	public List<Problem> selectAll() {
		List<Problem> problems = ProblemEntity.toDto(pr.findAll());
		return problems;
	}

	@Override
	public List<Problem> selectBySearchcondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}

}
