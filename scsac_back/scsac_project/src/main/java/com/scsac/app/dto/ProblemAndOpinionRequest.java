package com.scsac.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString	
@Builder
public class ProblemAndOpinionRequest {
	private Problem problem;
    private Opinion opinion;

}
