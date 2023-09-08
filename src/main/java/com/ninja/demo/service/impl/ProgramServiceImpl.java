package com.ninja.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninja.demo.dto.BatchDto;
import com.ninja.demo.dto.ProgramDto;
import com.ninja.demo.dto.ResponseDto;
import com.ninja.demo.entity.Batch;
import com.ninja.demo.entity.Program;
import com.ninja.demo.exceptions.AlreadyExistException;
import com.ninja.demo.exceptions.NotFoundException;
import com.ninja.demo.respository.ProgramRepository;
import com.ninja.demo.service.ProgramService;

@Service
public class ProgramServiceImpl implements ProgramService{
	
	@Autowired
	ProgramRepository programRepository;
	
	@Override
	public List<ProgramDto> getAllPrograms() {
	     
		final List<Program> programRecords = programRepository.findAll();
	     
		   List<ProgramDto>  programDtoList = new ArrayList<ProgramDto>();
		
		 //converting Entity to Dto
	     for (Program program : programRecords) {
			ProgramDto programDto = new ProgramDto();
			programDto.setProgramId(program.getProgramId());
			programDto.setProgramName(program.getProgramName());
			programDto.setProgramDescription(program.getProgramDescription());
			programDto.setProgramStatus(program.getProgramStatus());
			
			//add all batches
			Set<Batch> batches = program.getBatches();
			Set<BatchDto> batchDtoSet = new HashSet<BatchDto>();			
			 //converting Entity to Dto
			for (Batch batch : batches) {
				BatchDto batchDto = new BatchDto();
				batchDto.setBatchId(batch.getBatchId());
				batchDto.setBatchName(batch.getBatchName());
				batchDto.setBatchStatus(batch.getBatchStatus());
				batchDto.setBatchDescription(batch.getBatchDescription());
				batchDto.setBatchNoOfClasses(batch.getBatchNoOfClasses());
				
				batchDtoSet.add(batchDto);
			}
			programDto.setBatches(batchDtoSet);
			
		   programDtoList.add(programDto);	
		}
		return programDtoList;	     	     	     	     	     	     		     
		
	}

	
	@Override
	public ProgramDto createProgram(ProgramDto programDto) {
		
		//check program name exists or not
		boolean exists = programRepository.existsByProgramName(programDto.getProgramName());
		
		if(!exists) {
	    //converting Dto to Entity
		Program program = new Program();
		program.setProgramName(programDto.getProgramName());
		program.setProgramStatus(programDto.getProgramStatus());
		program.setProgramDescription(programDto.getProgramDescription());
		 
		Program saveProgram = programRepository.save(program);
		
		//converting Entity to Dto
		ProgramDto returnProgramDto = new ProgramDto();
		returnProgramDto.setProgramId(saveProgram.getProgramId());
		returnProgramDto.setProgramName(saveProgram.getProgramName());
		returnProgramDto.setProgramStatus(saveProgram.getProgramStatus());
		returnProgramDto.setProgramDescription(saveProgram.getProgramDescription());
			
		return returnProgramDto;
	 }
		throw new AlreadyExistException("Program with the name already exist'" + programDto.getProgramName() + "'");
	}

	
	@Override
	public ProgramDto updateProgram(int programId, ProgramDto programDto) {
		
		//checking program id is present or not
		Optional<Program> optional = programRepository.findById(programId);
		if (optional.isPresent()) {			
			Program program = optional.get();
			
			if(null != programDto.getProgramName()) {
				program.setProgramName(programDto.getProgramName());
			}
			if(null != programDto.getProgramDescription()) {
				program.setProgramDescription(programDto.getProgramDescription());
			}
			if(null != programDto.getProgramStatus()) {
				program.setProgramStatus(programDto.getProgramStatus());
			}
			
			program.setLastModTime(new Date());
			
			Program saveProgram = programRepository.save(program);
			//converting Entity to Dto
			ProgramDto returnProgramDto = new ProgramDto();
			returnProgramDto.setProgramName(saveProgram.getProgramName());
			returnProgramDto.setProgramStatus(saveProgram.getProgramStatus());
			returnProgramDto.setProgramDescription(saveProgram.getProgramDescription());
			returnProgramDto.setProgramId(saveProgram.getProgramId());
			
			return returnProgramDto;									
		} 
			// throw new ProgramNotFoundException();
			throw new NotFoundException("Program does not exist with id '" + programId + "'");
	}


	@Override
	public ResponseDto deleteProgram(int programId) {
		//checking program id is present or not
		Optional<Program> optional = programRepository.findById(programId);
		if(optional.isPresent()) {
			Program program = optional.get();	
			programRepository.deleteById(programId);
			
			return new ResponseDto("Success", "Program is deleted", new Date(), null);
		}
		//throw new IdNotFound
		throw new NotFoundException("Program does not exist with id '" + programId + "'");
		
	}	

}
