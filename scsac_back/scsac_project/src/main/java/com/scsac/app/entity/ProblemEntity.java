package com.scsac.app.entity;

import java.util.List;
import java.util.stream.Collectors;

import com.scsac.app.dto.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "problem")
public class ProblemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length=50, nullable = false)
	private String url;
	
	@Column(nullable = false,unique = true)
	private int problemNum;
	
	@Column(length=50, nullable = false)
	private String title;
	
	@Column(nullable = false)
	private float rate;
	
	@OneToMany(mappedBy = "problem")
	private List<OpinionEntity> opinions;

	
	public static Problem toDto(ProblemEntity p) {
		return new Problem(p.getId(),p.getUrl(),p.getProblemNum(),p.getTitle(),p.getRate(),null,OpinionEntity.toDto(p.getOpinions()));
	}
	
	public static List<Problem> toDto(List<ProblemEntity> ps){
		return ps.stream()
	             .map(ProblemEntity::toDto)
	             .collect(Collectors.toList());
	}
	
}
