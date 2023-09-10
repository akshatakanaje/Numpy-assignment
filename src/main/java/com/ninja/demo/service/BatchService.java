package com.ninja.demo.service;

import java.util.List;

import com.ninja.demo.dto.BatchDto;
import com.ninja.demo.dto.ResponseDto;

public interface BatchService {

	List<BatchDto> getAllBatches();

	BatchDto createBatch(BatchDto batchDto);

	BatchDto updateBatch(int batchId, BatchDto batchDto);

	ResponseDto deleteProgram(int batchId);

    BatchDto getOneBatch(int batchId);
}
