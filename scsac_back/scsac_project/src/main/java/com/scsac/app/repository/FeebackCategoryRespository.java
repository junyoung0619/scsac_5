package com.scsac.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scsac.app.entity.FeedbackCategoryEntity;

public interface FeebackCategoryRespository extends JpaRepository<FeedbackCategoryEntity, Integer> {

}
