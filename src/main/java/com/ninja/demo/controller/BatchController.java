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

import com.ninja.demo.dto.BatchDto;
import com.ninja.demo.dto.ResponseDto;
import com.ninja.demo.service.BatchService;

@RestController
@RequestMapping("/batches")
public class BatchController {
	
	@Autowired
    BatchService batchService;
	
	//Get all batch info
	@GetMapping
	public List<BatchDto> getAllBatches(){
		return batchService.getAllBatches();
	}

	//Get batch by batchId
	@GetMapping("/{batchId}")
	public BatchDto getOneBatch(@PathVariable ("batchId") int batchId){
		return batchService.getOneBatch(batchId);
	}
	
	//Create batch
	@PostMapping
	public BatchDto createBatch(@RequestBody BatchDto batchDto) {
		return batchService.createBatch(batchDto);
	}
	
	//Update batch info
	@PutMapping("/{batchId}")
	public BatchDto updateBatch(@RequestBody BatchDto batchDto, @PathVariable int batchId) {
		return batchService.updateBatch(batchId, batchDto);
	}

	//Delete batch
	@DeleteMapping("/{batchId}")
	ResponseDto deleteBatch(@PathVariable int batchId) {
			return batchService.deleteBatch(batchId);
	}

}
