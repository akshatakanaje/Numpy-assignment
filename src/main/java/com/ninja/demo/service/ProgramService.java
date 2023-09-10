package com.ninja.demo.service;

import java.util.List;
import java.util.Optional;

import com.ninja.demo.dto.ProgramDto;
import com.ninja.demo.dto.ResponseDto;

public interface ProgramService {

	List<ProgramDto> getAllPrograms();

	ProgramDto createProgram(ProgramDto programDto);
	
	ProgramDto updateProgram(int programId, ProgramDto programDto);

	ResponseDto deleteProgram(int programId);

	ProgramDto getOne(int programId);
}
