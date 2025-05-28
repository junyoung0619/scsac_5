package com.scsac.app.dto;

import java.util.List;

import com.scsac.app.entity.ProblemEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString	
@Builder
public class Problem {
	
	private int id;
	private String url;
	private int problemNum;
	private String title;
	private float rate;
	private List<String> categories;	
	private List<Opinion> opinions;
	
	public static ProblemEntity toEntity(Problem p) {
		return new ProblemEntity(p.getId(),p.getUrl(),p.getProblemNum(),p.getTitle(),p.getRate(),null);
	}
}
