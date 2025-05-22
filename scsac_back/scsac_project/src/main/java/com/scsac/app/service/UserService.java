package com.scsac.app.service;

import com.scsac.app.dto.User;

public interface UserService {
	User findbyId(String id);
	int insertUser(int num, int generation,String password);
	int insertUser(User user);
	int updateUser(User user);
}
