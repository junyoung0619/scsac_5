package com.scsac.app.dto;

import com.scsac.app.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	private String id;
	private String password;
	private int authority;
	private int generation;

	private String affiliate;
	private String name;
	private String nickname;
	private String bojId;

	public static UserEntity toEntity(User u) {
		return new UserEntity(u.getId(), u.getPassword(), u.getAuthority(), u.getGeneration(), u.getAffiliate(),
				u.getName(), u.getNickname(), u.getBojId());
	}
}
