package com.ninja.demo.dto;

import java.util.Date;
import java.util.Set;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProgramDto {
	
	private int programId;
	private String programName;
	private String programDescription;
	private String programStatus;
	private Date creationTime = new Date();
	private Date lastModTime = new Date();
	
	//fetching batch data 
	private Set<BatchDto> batches;
}
