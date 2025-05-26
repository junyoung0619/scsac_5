package com.scsac.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.OpinionEntity;
import com.scsac.app.entity.UserEntity;

@Repository
public interface OpinionRespository extends JpaRepository<OpinionEntity, Integer> {
	List<OpinionEntity> findByProblemId(int problemId);
}
