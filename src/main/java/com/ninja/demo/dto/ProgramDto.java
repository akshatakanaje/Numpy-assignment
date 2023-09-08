package com.ninja.demo.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgramDto {
	
	private int programId;
	private String programName;
	private String programDescription;
	private String programStatus;
	
	//fetching batch data 
	private Set<BatchDto> batches;

}
