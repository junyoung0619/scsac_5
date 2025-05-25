package com.scsac.app.entity;

import com.scsac.app.dto.Opinion;
import com.scsac.app.dto.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "opinion")
public class OpinionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
	
	private int rate;
	
	@Column(length=50)
	private String feedbackCategory;
	
	@Column(length=50)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String comment;
    
    public static Opinion toDto(OpinionEntity o) {
    	return new Opinion(o.getId(),o.getProblem().getId(),o.getRate(),o.getFeedbackCategory(),o.getCategory(),o.getComment());
    }
}
