package com.scsac.app.dto;

import java.util.Set;

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


}
