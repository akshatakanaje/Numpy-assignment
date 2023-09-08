package com.ninja.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninja.demo.dto.BatchDto;
import com.ninja.demo.entity.Batch;
import com.ninja.demo.respository.BatchRepository;
import com.ninja.demo.service.BatchService;

@Service
public class BatchServiceImpl implements BatchService{

	@Autowired
	BatchRepository batchRepository;

	@Override
	public List<BatchDto> getAllBatches() {
		final List<Batch> batchRecords = batchRepository.findAll();
		return batchRecords.stream()
				.map(x -> new BatchDto(x.getBatchId(), x.getBatchName(), x.getBatchDescription(), x.getBatchStatus(), x.getBatchNoOfClasses())
		).collect(Collectors.toList());
	}

	
	
	

}
