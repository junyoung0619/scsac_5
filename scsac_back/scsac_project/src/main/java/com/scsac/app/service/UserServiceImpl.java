package com.scsac.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scsac.app.dto.User;
import com.scsac.app.entity.UserEntity;
import com.scsac.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository ur;

	@Override
	public User findbyId(String id) {
		Optional<UserEntity> e = ur.findById(id);
		if(e.isPresent()) return UserEntity.toDto(e.get());
		else		      return null;
	}
	
	@Override
	@Transactional
	public int insertUser(int num, int generation, String password) {
		int r = 0;
		try {
			for(int i=1;i<=num;i++) {
		        UserEntity e = new UserEntity();
		        e.setId(String.valueOf(generation)+String.format("%02d", num));
		        e.setPassword(password);
		        e.setAuthority(3);
		        e.setGeneration(generation);
	
		        UserEntity saved = ur.save(e);
		         r+=saved.getId() != null ? 1 : 0;
			}
	        if(r==num) {
	        	return r;
	        } else {
	        	throw new RuntimeException("오류 발생");    	
	        }

	    } catch (Exception ex) {
	        ex.printStackTrace(); // 실제로는 로깅 처리
	        return 0;
	    }
	}
	
	
	@Override
	@Transactional
	public int insertUser(User user) {
	    try {
	        UserEntity e = new UserEntity();
	        e.setId(String.valueOf(user.getId()));
	        e.setPassword(user.getPassword());
	        e.setAuthority(user.getAuthority());
	        e.setGeneration(user.getGeneration());

	        UserEntity saved = ur.save(e);
	        
	        return saved.getId() != null ? 1 : 0;

	    } catch (Exception ex) {
	        ex.printStackTrace(); // 실제로는 로깅 처리
	        return 0;
	    }
	}

	
	@Override
	@Transactional
	public int updateUser(User user) {
		Optional<UserEntity> e = ur.findById(String.valueOf(user.getId()));
		if(e.isEmpty()) return 0;
		
		User tmp_user = UserEntity.toDto(e.get());
		tmp_user.setPassword(user.getPassword());
		tmp_user.setAuthority(user.getAuthority());
		tmp_user.setNickname(user.getNickname());
		tmp_user.setBojId(user.getBojId());
		
		return 1;
	}





}
