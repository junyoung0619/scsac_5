package com.scsac.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public User findById(String id) {
		Optional<UserEntity> e = ur.findById(id);
		if (e.isPresent())
			return UserEntity.toDto(e.get());
		else
			return null;
	}

	@Override
	@Transactional
	public int insertUser(int num, int generation, String password) {
		int r = 0;
		try {
			for (int i = 1; i <= num; i++) {
				UserEntity e = new UserEntity();
				e.setId(String.format("%02d", generation) + String.format("%02d", i));
				e.setPassword(passwordEncoder.encode(password));
				e.setAuthority(3);
				e.setGeneration(generation);

				UserEntity saved = ur.save(e);
				r += saved.getId() != null ? 1 : 0;
			}
			if (r == num) {
				return 1;
			} else {
				throw new RuntimeException("오류 발생");
			}

		} catch (Exception ex) {
			ex.printStackTrace(); 
			return 0;
		}
	}

	@Override
	@Transactional
	public int updateUser(User user) {
		Optional<UserEntity> e = ur.findById(String.valueOf(user.getId()));
		if (e.isEmpty())
			return 0;

		UserEntity tmp_user = e.get();
		tmp_user.setPassword(passwordEncoder.encode(user.getPassword()));
		tmp_user.setAuthority(user.getAuthority());
		tmp_user.setAffiliate(user.getAffiliate());
		tmp_user.setName(user.getName());
		tmp_user.setNickname(user.getNickname());
		tmp_user.setBojId(user.getBojId());

		return 1;
	}

	@Override
	@Transactional
	public int updateAuthority(int generation) {
		int r = ur.updateAuthority(generation);
		if (r == 0)
			return 0;
		return 1;
	}

}
