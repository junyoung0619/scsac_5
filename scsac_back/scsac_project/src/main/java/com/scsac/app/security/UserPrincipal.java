package com.scsac.app.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scsac.app.dto.User;
import com.scsac.app.entity.UserEntity;
import com.scsac.app.entity.UserRole;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
	private final UserEntity user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    UserRole role = UserRole.transform(user.getAuthority(), user.getName());
		return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getId();
	}
	
    // 계정 만료 여부 (true = 사용 가능)
    @Override public boolean isAccountNonExpired() { return true; }

    // 계정 잠김 여부 (true = 사용 가능)
    @Override public boolean isAccountNonLocked() { return true; }

    // 비밀번호 만료 여부
    @Override public boolean isCredentialsNonExpired() { return true; }

    // 사용자 활성화 여부
    @Override public boolean isEnabled() { return true; }
	
}
