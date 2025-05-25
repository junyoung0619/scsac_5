package com.scsac.app.dto;

import java.util.Set;

import com.scsac.app.entity.CategoryEntity;
import com.scsac.app.entity.FeedbackCategoryEntity;
import com.scsac.app.entity.OpinionEntity;

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
public class Opinion {

    private int id;
    private int problemId;
    private int rate;
    private String feedbackCategory;
    private String category;
    private String comment;
    public OpinionEntity toEntity(Problem problem,
            Set<CategoryEntity> categoryEntities,
            Set<FeedbackCategoryEntity> feedbackCategoryEntities) {
			return OpinionEntity.builder()
			.id(this.id)
			.problem(problem.toDto(problem))
			.rate(this.rate)
			.comment(this.comment)
			.categories(categoryEntities)
			.feedbackCategories(feedbackCategoryEntities)
			.build();
	}



}
