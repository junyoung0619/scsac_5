package com.scsac.app.service;

import com.scsac.app.dto.User;

public interface UserService {
	
	User findById(String id);
	int isExist(int generation);
	int insertUser(int num, int generation,String password);
	int updateUser(User user);
	int updateAuthority(int generation);
	int addAdmin(String id);
}
