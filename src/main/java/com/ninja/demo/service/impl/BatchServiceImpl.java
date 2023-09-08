package com.ninja.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninja.demo.dto.BatchDto;
import com.ninja.demo.dto.ResponseDto;
import com.ninja.demo.entity.Batch;
import com.ninja.demo.entity.Program;
import com.ninja.demo.exceptions.AlreadyExistException;
import com.ninja.demo.exceptions.NotFoundException;
import com.ninja.demo.respository.BatchRepository;
import com.ninja.demo.respository.ProgramRepository;
import com.ninja.demo.service.BatchService;

@Service
public class BatchServiceImpl implements BatchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramServiceImpl.class);

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    ProgramRepository programRepository;


    @Override
    public List<BatchDto> getAllBatches() {
        LOGGER.info("Getting all batches");

        final List<Batch> batchRecords = batchRepository.findAll();

        List<BatchDto> batchDtoList = new ArrayList<BatchDto>();
        //convert Entity to Dto
        for (Batch batch : batchRecords) {
            BatchDto batchDto = new BatchDto();
            batchDto.setBatchId(batch.getBatchId());
            batchDto.setBatchName(batch.getBatchName());
            batchDto.setBatchDescription(batch.getBatchDescription());
            batchDto.setBatchStatus(batch.getBatchStatus());
            batchDto.setBatchNoOfClasses(batch.getBatchNoOfClasses());
            batchDto.setProgramId(batch.getProgram().getProgramId());

            batchDtoList.add(batchDto);
        }
        //return the list
        return batchDtoList;

    }


    @Override
    public BatchDto createBatch(BatchDto batchDto) {
        LOGGER.info("Creating new batch");

        //checking batch with the name exists or not
        if (batchRepository
                .existsByBatchNameAndProgramProgramId(batchDto.getBatchName(), batchDto.getProgramId())) {
            String message = String.format("Batch with the name=%s and programId=%d already exists",
                    batchDto.getBatchName(), batchDto.getProgramId());
            throw new AlreadyExistException(message);
        }

        //checking program id is present or not because without program id there is no batches
        Optional<Program> optional = programRepository.findById(batchDto.getProgramId());
        if (optional.isPresent()) {
            Program program = optional.get();

            //converting Dto to program
            Batch batch = new Batch();
            batch.setBatchName(batchDto.getBatchName());
            batch.setBatchDescription(batchDto.getBatchDescription());
            batch.setBatchStatus(batchDto.getBatchStatus());
            batch.setBatchNoOfClasses(batchDto.getBatchNoOfClasses());

            //link the program with batch
            batch.setProgram(program);

            Batch saveBatch = batchRepository.save(batch);

            //converting Entity to Dto
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


    @Override
    public BatchDto updateBatch(int batchId, BatchDto batchDto) {
        LOGGER.info("Updating batch by batchId={}", batchId);

        //checking batch id
        Optional<Batch> optional = batchRepository.findById(batchId);
        if (optional.isPresent()) {
            Batch batch = optional.get();

            if (null != batchDto.getBatchName()) {
                batch.setBatchName(batchDto.getBatchName());
            }
            if (null != batchDto.getBatchDescription()) {
                batch.setBatchDescription(batchDto.getBatchDescription());
            }
            if (null != batchDto.getBatchStatus()) {
                batch.setBatchStatus(batchDto.getBatchStatus());
            }
            if (null != String.valueOf(batchDto.getBatchNoOfClasses())) {
                batch.setBatchNoOfClasses(batchDto.getBatchNoOfClasses());
            }

            batch.setLastModTime(new Date());

            Batch saveBatch = batchRepository.save(batch);
            //converting Entity to Dto
            BatchDto returnBatchDto = new BatchDto();
            returnBatchDto.setBatchId(saveBatch.getBatchId());
            returnBatchDto.setBatchName(saveBatch.getBatchName());
            returnBatchDto.setBatchDescription(saveBatch.getBatchDescription());
            returnBatchDto.setBatchStatus(saveBatch.getBatchStatus());
            returnBatchDto.setBatchNoOfClasses(saveBatch.getBatchNoOfClasses());
            returnBatchDto.setProgramId(saveBatch.getProgram().getProgramId());

            return returnBatchDto;
        }

        // throw new BatchNotFoundException();
        throw new NotFoundException("Batch does not exist with id '" + batchId + "'");
    }


    @Override
    public ResponseDto deleteProgram(int batchId) {
        LOGGER.info("Deleting batch by batchId={}", batchId);

        //checking batch id
        Optional<Batch> optional = batchRepository.findById(batchId);
        if (optional.isPresent()) {
            Batch batch = optional.get();
            batchRepository.deleteById(batchId);

            return new ResponseDto("Success", "Batch is deleted", new Date(), null);
        }
        //throw new IdNotFound
        throw new NotFoundException("Batch does not exist with id '" + batchId + "'");
    }
}
