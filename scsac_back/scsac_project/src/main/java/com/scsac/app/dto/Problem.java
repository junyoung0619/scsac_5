package com.scsac.app.dto;

import com.scsac.app.entity.ProblemEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {
	
	private int id;
	private String url;
	private int problem_num;
	private String title;
	private int rate;
	
	public static ProblemEntity toDto(Problem p) {
		return new ProblemEntity(p.getId(),p.getUrl(),p.getProblem_num(),p.getTitle(),p.getRate());
	}
}
