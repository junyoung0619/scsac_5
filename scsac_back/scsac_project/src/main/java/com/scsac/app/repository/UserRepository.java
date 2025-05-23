package com.scsac.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsac.app.dto.User;
import com.scsac.app.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
	Optional<UserEntity> findById(String id); 
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE UserEntity u SET u.authoriy= 2 WHERE u.generation = :generation")
	int updateAuthority(int generation);
}
