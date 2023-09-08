package com.ninja.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchDto {

	private int batchId;
	private String batchName;
	private String batchDescription;
	private String batchStatus;
	private int batchNoOfClasses;
	
}
