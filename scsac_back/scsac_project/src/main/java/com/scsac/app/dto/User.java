package com.scsac.app.dto;

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
	private int id;
	private String password;
	private int authority;
	private int generation;
	
	private String affiliate;
	private String name;
	private String nickname;
	private String bojId;
}
