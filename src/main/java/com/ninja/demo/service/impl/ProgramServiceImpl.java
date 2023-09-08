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
		
		 //converting program to programDto
	     for (Program program : programRecords) {
			ProgramDto programDto = new ProgramDto();
			programDto.setProgramId(program.getProgramId());
			programDto.setProgramName(program.getProgramName());
			programDto.setProgramDescription(program.getProgramDescription());
			programDto.setProgramStatus(program.getProgramStatus());
			
			//add all batches
			Set<Batch> batches = program.getBatches();
			Set<BatchDto> batchDtoSet = new HashSet<BatchDto>();			
			//converting batch to batchDto
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
	     /*
	     return programRecords.stream()
	    		 .map(p -> {
	    			 final ProgramDto programDto  = new ProgramDto();
	    			 programDto.setProgramId(p.getProgramId());
	    			 programDto.setProgramName(p.getProgramName());
	    			 programDto.setProgramStatus(p.getProgramStatus());
	    			 programDto.setProgramDescription(p.getProgramDescription());
	    			 	    			 
	    			 // add all the batches 
	    			 Set<Batch> batches = p.getBatches();
	    			 Set<BatchDto> batchDtos = 
	    					 batches.stream()
	    					 .map(b -> new BatchDto(b.getBatchId(), 
	    							 b.getBatchName(), b.getBatchStatus(),
	    							 b.getBatchDescription(), 
	    							 b.getBatchNoOfClasses())
	    		).collect(Collectors.toSet());	    			 
	    			 programDto.setBatches(batchDtos);	    			 
	    			 return programDto;	    		 
	    		}	     
	  ).collect(Collectors.toList());
	  */
		
	}

	
	@Override
	public ProgramDto createProgram(ProgramDto programDto) {
		
		//check program name exists or not
		boolean exists = programRepository.existsByProgramName(programDto.getProgramName());
		
		if(!exists) {		
		Program program = new Program();
		program.setProgramName(programDto.getProgramName());
		program.setProgramStatus(programDto.getProgramStatus());
		program.setProgramDescription(programDto.getProgramDescription());
		 
		Program saveProgram = programRepository.save(program);
		
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
