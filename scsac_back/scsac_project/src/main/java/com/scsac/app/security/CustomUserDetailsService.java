package com.scsac.app.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scsac.app.entity.UserEntity;
import com.scsac.app.entity.UserRole;
import com.scsac.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findById(username)
							.orElseThrow(()->new UsernameNotFoundException("사용자가 존재하지 않습니다."));
		
		return new User(user.getId(), user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + UserRole.transform(user.getAuthority(), user.getName()).name())));
	}

}
