package com.scsac.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.FeedbackCategoryEntity;

@Repository
public interface FeedbackCategoryRepository extends JpaRepository<FeedbackCategoryEntity, Integer>{
	Optional<FeedbackCategoryEntity> findByName(String name);
	List<FeedbackCategoryEntity> findById(int id);
}
