package com.scsac.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scsac.app.dto.Category;
import com.scsac.app.entity.CategoryEntity;
import com.scsac.app.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository cr;

	@Override
	public List<Category> selectAll() {
		List<CategoryEntity> e = cr.findAll();
		
		if (e==null) return null;
		
		return CategoryEntity.toDto(e);
	}
	
	
}
