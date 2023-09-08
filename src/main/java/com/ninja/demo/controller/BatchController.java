package com.ninja.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninja.demo.dto.BatchDto;
import com.ninja.demo.service.BatchService;

@RestController
@RequestMapping("/batches")
public class BatchController {
	
	@Autowired
    BatchService batchService;
	
	//get all batch info
	@GetMapping
	public List<BatchDto> getAllBatches(){
		return batchService.getAllBatches();
	}
	

}
