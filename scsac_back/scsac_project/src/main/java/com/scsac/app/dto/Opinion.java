package com.scsac.app.dto;

import java.util.List;
import java.util.Set;

import com.scsac.app.entity.CategoryEntity;
import com.scsac.app.entity.FeedbackCategoryEntity;
import com.scsac.app.entity.OpinionEntity;
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
public class Opinion {

    private int id;
    private int problemId;
    private int rate;
    private List<String> feedbackCategory;
    private List<String> category;
    private String comment;
    public static OpinionEntity fromDto(
    	    Opinion dto,
    	    ProblemEntity problem,
    	    Set<CategoryEntity> categoryEntities,
    	    Set<FeedbackCategoryEntity> feedbackCategoryEntities
    	) {
    	    return OpinionEntity.builder()
    	        .id(dto.getId())
    	        .problem(problem)
    	        .rate(dto.getRate())
    	        .comment(dto.getComment())
    	        .categories(categoryEntities)
    	        .feedbackCategories(feedbackCategoryEntities)
    	        .build();
    	}



}
