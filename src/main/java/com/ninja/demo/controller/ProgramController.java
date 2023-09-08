package com.ninja.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninja.demo.dto.ProgramDto;
import com.ninja.demo.dto.ResponseDto;
import com.ninja.demo.exceptions.AlreadyExistException;
import com.ninja.demo.service.ProgramService;

@RestController
@RequestMapping("/programs")
public class ProgramController {
	
	@Autowired
	ProgramService programService;
	
	//Get all program details
	@GetMapping
	public List<ProgramDto> getAllPrograms(){
		return programService.getAllPrograms();
	}
	
	
	//Create new program
	@PostMapping
	public ProgramDto createProgram(@RequestBody ProgramDto programDto) {
		return programService.createProgram(programDto);
	}
	
	//Update program info
	@PutMapping("/{programId}")
	public ProgramDto updateProgram(@RequestBody ProgramDto programDto, @PathVariable int programId) {
		return programService.updateProgram(programId, programDto);
	}
		
	//Delete program
	@DeleteMapping("/{programId}")
	ResponseDto deleteProgram(@PathVariable int programId) {
		return programService.deleteProgram(programId);
		
	}		

}
