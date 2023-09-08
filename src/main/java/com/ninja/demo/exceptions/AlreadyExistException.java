package com.ninja.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlreadyExistException extends RuntimeException{
		
	private static final long serialVersionUID = 2315783994437555895L;
	
	private String message;

}
