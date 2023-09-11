package com.ninja.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ninja.demo.exceptions.BadRequestException;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);
	
	@Autowired
	ProgramRepository programRepository;

	@Override
	public List<ProgramDto> getAllPrograms() {
		LOGGER.info("Getting all programs");
		final List<Program> programRecords = programRepository.findAll();
	     
		   List<ProgramDto>  programDtoList = new ArrayList<ProgramDto>();
		
		 //converting Entity to Dto
	     for (Program program : programRecords) {
			ProgramDto programDto = new ProgramDto();
			programDto.setProgramId(program.getProgramId());
			programDto.setProgramName(program.getProgramName());
			programDto.setProgramDescription(program.getProgramDescription());
			programDto.setProgramStatus(program.getProgramStatus());
			programDto.setCreationTime(program.getCreationTime());
			programDto.setLastModTime(program.getLastModTime());
			
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
		LOGGER.info("Creating new program");

		//validating program name for null and empty
		if(null == programDto.getProgramName() || programDto.getProgramName().trim().isEmpty()){
			String message = String.format("Program name must not be null and empty");
			throw new BadRequestException(message);
		}

		//check program name exists or not
		boolean exists = programRepository.existsByProgramName(programDto.getProgramName());
		if(!exists) {
	    //converting Dto to Entity
		Program program = new Program();
		program.setProgramName(programDto.getProgramName());
		program.setProgramStatus(programDto.getProgramStatus());
		program.setProgramDescription(programDto.getProgramDescription());
		program.setCreationTime(programDto.getCreationTime());
		program.setLastModTime(programDto.getLastModTime());
		 
		Program saveProgram = programRepository.save(program);
		//converting Entity to Dto
		ProgramDto returnProgramDto = new ProgramDto();
		returnProgramDto.setProgramId(saveProgram.getProgramId());
		returnProgramDto.setProgramName(saveProgram.getProgramName());
		returnProgramDto.setProgramStatus(saveProgram.getProgramStatus());
		returnProgramDto.setProgramDescription(saveProgram.getProgramDescription());
		returnProgramDto.setCreationTime(saveProgram.getCreationTime());
		returnProgramDto.setLastModTime(saveProgram.getLastModTime());
		return returnProgramDto;
	 }
		String message = String.format("Program with the name already exist'" + programDto.getProgramName() + "'");
		throw new AlreadyExistException(message);
	}

	@Override
	public ProgramDto updateProgram(int programId, ProgramDto programDto) {
		LOGGER.info("Updating program by programId={}", programId);
		
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
			returnProgramDto.setCreationTime(saveProgram.getCreationTime());
			returnProgramDto.setLastModTime(saveProgram.getLastModTime());
			return returnProgramDto;									
		} 
			// throw new ProgramNotFoundException();
		    String message = String.format("Program does not exist with id '" + programId + "'");
			throw new NotFoundException(message);
	}

	@Override
	public ResponseDto deleteProgram(int programId) {
		LOGGER.info("Deleting program by programId={}", programId);
		
		//checking program id is present or not
		Optional<Program> optional = programRepository.findById(programId);
		if(optional.isPresent()) {
			Program program = optional.get();	
			programRepository.deleteById(programId);
			return new ResponseDto("Success", "Program is deleted", new Date());
		}
		//throw new NotFound
		String message = String.format("Program does not exist with id '" + programId + "'");
		throw new NotFoundException(message);
	}

	@Override
	public ProgramDto getOne(int programId) {
		LOGGER.info("Getting program by programId={}", programId);

		Optional<Program> optional = programRepository.findById(programId);
		if (optional.isPresent()){
			Program program = optional.get();
            //converting Entity to Dto
			ProgramDto programDto = new ProgramDto();
			programDto.setProgramId(program.getProgramId());
			programDto.setProgramName(program.getProgramName());
			programDto.setProgramStatus(program.getProgramStatus());
			programDto.setProgramDescription(program.getProgramDescription());
            programDto.setCreationTime(program.getCreationTime());
			programDto.setLastModTime(program.getLastModTime());

			//get all batches
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
			return programDto;
		}
		String message = String.format("Program does not exist with id '" + programId + "'");
		throw new NotFoundException(message);
	}
}
