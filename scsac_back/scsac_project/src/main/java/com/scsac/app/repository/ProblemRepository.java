package com.scsac.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.ProblemEntity;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer> {
	
	ProblemEntity findById(int id);
	
    List<ProblemEntity> findByProblemNum(int problemNum);

    List<ProblemEntity> findByTitleContaining(String title);

    List<ProblemEntity> findByRateGreaterThanEqual(int rate);
    
    @Query("""
    	    SELECT DISTINCT o.problem
    	    FROM OpinionEntity o
    	    JOIN o.categories c
    	    WHERE c.name = :categoryName
    	""")
    List<ProblemEntity> findProblemsByCategoryName(String categoryName);
    void deleteById(int id);

}
