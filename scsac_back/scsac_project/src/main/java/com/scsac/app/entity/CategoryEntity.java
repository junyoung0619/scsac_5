package com.scsac.app.entity;

import java.util.List;
import java.util.stream.Collectors;

import com.scsac.app.dto.Category;

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
@Table(name = "category")
public class CategoryEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length=50)
    private String name;
    
    public static Category toDto(CategoryEntity c) {
    	return new Category(c.getId(),c.getName());
    }
    
    public static List<Category> toDto(List<CategoryEntity> cs) {
    	return cs.stream()
    			.map(CategoryEntity::toDto)
    			.collect(Collectors.toList());
    }
}