package com.scsac.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scsac.app.entity.CategoryEntity;

public interface CategoryRespository extends JpaRepository<CategoryEntity, Integer> {

}
