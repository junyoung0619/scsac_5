package com.scsac.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scsac.app.entity.OpinionEntity;
import com.scsac.app.entity.UserEntity;

public interface OpinionRespository extends JpaRepository<UserEntity, String> {
	List<OpinionEntity> findByProblemId(int problemId);
}
