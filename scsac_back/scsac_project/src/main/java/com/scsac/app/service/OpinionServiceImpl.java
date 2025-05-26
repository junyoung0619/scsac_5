package com.scsac.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scsac.app.dto.Opinion;
import com.scsac.app.entity.OpinionEntity;
import com.scsac.app.repository.OpinionRespository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OpinionServiceImpl implements OpinionService {

	private final OpinionRespository or;
	@Override
	public List<Opinion> findByProblemId(int problemId) {
		List<OpinionEntity> opinions = or.findByProblemId(problemId);
		if(opinions==null) {
			return null;
		}
		return OpinionEntity.toDto(opinions);
	}

}
