package com.ninja.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BatchDto {

	private int batchId;
	private String batchName;
	private String batchDescription;
	private String batchStatus;
	private int batchNoOfClasses;
	
	private int programId;
	
}
