package com.scsac.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scsac.app.entity.UserEntity;

public interface CategoryRespository extends JpaRepository<UserEntity, String> {

}
