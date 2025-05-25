package com.scsac.app.entity;

import java.util.HashSet;
import java.util.Set;

import com.scsac.app.dto.Opinion;
import com.scsac.app.dto.Problem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    private ProblemEntity problem;
	
	private int rate;

	@Column(columnDefinition = "TEXT")
	private String comment;
	
	@Column(length=50)
	private String feedbackCategory;
	
	@ManyToMany
    @JoinTable(name = "opinion_category",
        joinColumns = @JoinColumn(name = "opinion_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> categories = new HashSet();

    @ManyToMany
    @JoinTable(name = "opinion_feedback_category",
        joinColumns = @JoinColumn(name = "opinion_id"),
        inverseJoinColumns = @JoinColumn(name = "feedback_category_id"))
    private Set<FeedbackCategoryEntity> feedbackCategories = new HashSet<>();

    public Opinion toDto() {
        return Opinion.builder()
            .id(this.id)
            .problemId(this.problem != null ? this.problem.getId() : 0)
            .rate(this.rate)
            .comment(this.comment)
            .category(this.categories.stream()
                .map(CategoryEntity::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse(null))
            .feedbackCategory(this.feedbackCategories.stream()
                .map(FeedbackCategoryEntity::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse(null))
            .build();
    }

    
}

