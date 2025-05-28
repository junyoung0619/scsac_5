package com.scsac.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsac.app.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	Optional<UserEntity> findById(String id); 
	List<UserEntity> findBygeneration(int generation);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE UserEntity u SET u.authority= 2 WHERE u.generation = :generation and authority != 1")
	int updateAuthority(int generation);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE UserEntity u SET u.authority= 1 WHERE u.id = :id")
	int addAdmin(String id);
}
