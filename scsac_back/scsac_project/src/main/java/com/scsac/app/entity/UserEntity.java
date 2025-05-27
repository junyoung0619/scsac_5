package com.scsac.app.entity;

import com.scsac.app.dto.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {

	@Id
	@Column(length=10)
	private String id;

	@Column(length = 100, nullable = false)
	private String password;

	@Column(nullable = false, columnDefinition = "INT DEFAULT 3")
	private int authority;

	@Column(nullable = false)
	private int generation;

	@Column(length = 10)
	private String affiliate;

	@Column(length = 45)
	private String name;

	@Column(length = 45)
	private String nickname;

	@Column(length = 100)
	private String bojId;

	public static User toDto(UserEntity u) {
		return new User(u.getId(), u.getPassword(), u.getAuthority(), u.getGeneration(), u.getAffiliate(), u.getName(),
				u.getNickname(), u.getBojId());

	}
}
