package com.scsac.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scsac.app.dto.FeedbackCategory;
import com.scsac.app.entity.FeedbackCategoryEntity;
import com.scsac.app.repository.FeedbackCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackCategoryServiceImpl implements FeedbackCategoryService {

	private final FeedbackCategoryRepository fr;
	
	@Override
	public List<FeedbackCategory> selectAll() {
		List<FeedbackCategoryEntity> fe = fr.findAll();

		return FeedbackCategoryEntity.toDto(fe);
	}

}
