package com.scsac.app.dto;

import com.scsac.app.entity.CategoryEntity;

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
public class Category {
	private int id;
    private String name;
    
    public static CategoryEntity toEntity(Category c) {
    	return new CategoryEntity(c.getId(),c.getName());
    }
}
