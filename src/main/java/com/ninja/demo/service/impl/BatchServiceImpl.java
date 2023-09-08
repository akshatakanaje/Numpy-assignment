package com.ninja.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninja.demo.dto.BatchDto;
import com.ninja.demo.entity.Batch;
import com.ninja.demo.entity.Program;
import com.ninja.demo.exceptions.AlreadyExistException;
import com.ninja.demo.exceptions.NotFoundException;
import com.ninja.demo.respository.BatchRepository;
import com.ninja.demo.respository.ProgramRepository;
import com.ninja.demo.service.BatchService;

@Service
public class BatchServiceImpl implements BatchService{

	@Autowired
	BatchRepository batchRepository;
	
	@Autowired
	ProgramRepository programRepository;


	@Override
	public List<BatchDto> getAllBatches() {
		final List<Batch> batchRecords = batchRepository.findAll();
		
		List<BatchDto> batchDtoList = new ArrayList<BatchDto>();
		for (Batch batch : batchRecords) {
			BatchDto batchDto = new BatchDto();
			batchDto.setBatchId(batch.getBatchId());
			batchDto.setBatchName(batch.getBatchName());
			batchDto.setBatchDescription(batch.getBatchDescription());
			batchDto.setBatchStatus(batch.getBatchStatus());
			batchDto.setBatchNoOfClasses(batch.getBatchNoOfClasses());
			
			 batchDtoList.add(batchDto);
		}
		return batchDtoList;

	}


	@Override
	public BatchDto createBatch( BatchDto batchDto) {
		
		//checking batch with the name exists or not
		if(batchRepository.existsByBatchName(batchDto.getBatchName())) {
			throw new AlreadyExistException("Batch with the name already exists");
		}
				
		Optional<Program> optional = programRepository.findById(batchDto.getProgramId());
		if(optional.isPresent()) {	
			Program program = optional.get();
			
			Batch batch = new Batch();
			batch.setBatchName(batchDto.getBatchName());
			batch.setBatchDescription(batchDto.getBatchDescription());
			batch.setBatchStatus(batchDto.getBatchStatus());
			batch.setBatchNoOfClasses(batchDto.getBatchNoOfClasses());
			
			batch.setProgram(program);
			
			Batch saveBatch = batchRepository.save(batch);
			
			BatchDto returnBatchDto = new BatchDto();
			returnBatchDto.setBatchId(saveBatch.getBatchId());
			returnBatchDto.setBatchName(saveBatch.getBatchName());
			returnBatchDto.setBatchDescription(saveBatch.getBatchDescription());
			returnBatchDto.setBatchStatus(saveBatch.getBatchStatus());
			returnBatchDto.setBatchNoOfClasses(saveBatch.getBatchNoOfClasses());
			returnBatchDto.setProgramId(saveBatch.getProgram().getProgramId());
			
			return returnBatchDto;
	   }
		
		throw new NotFoundException("Program id does not exist with id '" + batchDto.getProgramId() + "'");

	}
}
