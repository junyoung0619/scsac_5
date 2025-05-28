package com.scsac.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.OpinionEntity;
import com.scsac.app.entity.UserEntity;

@Repository
public interface OpinionRepository extends JpaRepository<OpinionEntity, Integer> {
	List<OpinionEntity> findByProblemId(int problemId);
	void deleteById(int id);

	@Query("SELECT o.problem.id FROM OpinionEntity o WHERE o.id = :opinionId")
	Optional<Integer> findProblemIdByOpinionId(int opinionId);
}
