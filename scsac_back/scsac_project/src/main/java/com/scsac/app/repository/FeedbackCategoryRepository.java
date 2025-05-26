package com.scsac.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.FeedbackCategoryEntity;

@Repository
public interface FeedbackCategoryRepository extends JpaRepository<FeedbackCategoryEntity, Integer>{

}
