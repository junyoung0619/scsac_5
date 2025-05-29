package com.scsac.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.ProblemEntity;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Integer> {
	
	Optional<ProblemEntity> findById(int id);
	
    Page<ProblemEntity> findByProblemNum(int problemNum,Pageable pageable);

    Page<ProblemEntity> findByTitleContaining(String title,Pageable pageable);

    Page<ProblemEntity> findByRateGreaterThanEqual(int rate,Pageable pageable);
    
    @Query("""
    	    SELECT DISTINCT o.problem
    	    FROM OpinionEntity o
    	    JOIN o.categories c
    	    WHERE c.name = :categoryName
    	""")
    Page<ProblemEntity> findProblemsByCategoryName(String categoryName,Pageable pageable);
    void deleteById(int id);

}
