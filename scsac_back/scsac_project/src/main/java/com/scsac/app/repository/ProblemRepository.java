package com.scsac.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.ProblemEntity;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer> {
	
	 // 조건: problemNum으로 찾기
    List<ProblemEntity> findByProblemNum(int problemNum);

    // 조건: title이 일치하는 문제 찾기
    List<ProblemEntity> findByTitle(String title);

    // 조건: rate이 특정 값 이상인 문제들
    List<ProblemEntity> findByRateGreaterThanEqual(int rate);
    
    @Query("""
    	    SELECT DISTINCT o.problem
    	    FROM OpinionEntity o
    	    JOIN o.categories c
    	    WHERE c.name = :categoryName
    	""")
    List<ProblemEntity> findProblemsByCategoryName(String categoryName);
}
