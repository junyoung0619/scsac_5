package com.scsac.app.entity;


import com.scsac.app.dto.FeedbackCategory;

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
@Table(name = "feedback_category")
public class FeedbackCategoryEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length=50)
    private String name;
    
    public static FeedbackCategory toDto(FeedbackCategoryEntity e) {
    	return new FeedbackCategory(e.getId(),e.getName());
    }
}