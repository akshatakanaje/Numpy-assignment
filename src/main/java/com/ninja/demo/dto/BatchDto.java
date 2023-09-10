package com.ninja.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
	private Date creationTime = new Date();
	private Date lastModTime = new Date();
	
	private Integer programId;
	
}
