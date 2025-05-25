package com.scsac.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scsac.app.entity.UserEntity;

public interface FeebackCategoryRespository extends JpaRepository<UserEntity, String> {

}
