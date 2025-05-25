package com.scsac.app.dto;

import com.scsac.app.entity.CategoryEntity;
import com.scsac.app.entity.FeedbackCategoryEntity;

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
public class FeedbackCategory {
	private int id;
	private String name;

	public static FeedbackCategoryEntity toEntity(FeedbackCategory f) {
		return new FeedbackCategoryEntity(f.getId(), f.getName());
	}
}
