package com.ninja.demo.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.ninja.demo.dto.ResponseDto;
import com.ninja.demo.exceptions.AlreadyExistException;
import com.ninja.demo.exceptions.NotFoundException;

public class GlobalExceptionHandler {
	
	ResponseDto responseDto;

	@ExceptionHandler(value = AlreadyExistException.class)
	public ResponseEntity<ResponseDto> alreadyExistException(AlreadyExistException ex) {
		responseDto = new ResponseDto(HttpStatus.CONFLICT.toString(), ex.getMessage(),new Date(), null );
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.CONFLICT);
	}


	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<ResponseDto> notFoundException(NotFoundException ex) {
		responseDto = new ResponseDto(HttpStatus.NOT_FOUND.toString(), ex.getMessage(),new Date(), null );
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
	}

}
