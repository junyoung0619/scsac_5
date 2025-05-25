package com.scsac.app.entity;

import com.scsac.app.dto.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "problem")
public class ProblemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length=50, nullable = false)
	private String url;
	
	@Column(nullable = false)
	private int problem_num;
	
	@Column(length=50, nullable = false)
	private String title;
	
	@Column(nullable = false)
	private int rate;
	
	public static Problem toDto(ProblemEntity p) {
		return new Problem(p.getId(),p.getUrl(),p.getProblem_num(),p.getTitle(),p.getRate());
	}
	
}
