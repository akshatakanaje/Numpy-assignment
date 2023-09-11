package com.ninja.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ninja.demo.exceptions.BadRequestException;
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
            batchDto.setCreationTime(batch.getCreationTime());
            batchDto.setLastModTime(batch.getLastModTime());

            batchDtoList.add(batchDto);
        }
        //return the list
        return batchDtoList;
    }


    @Override
    public BatchDto createBatch(BatchDto batchDto) {
        LOGGER.info("Creating new batch");

        //validating batch name for null & empty
        if(null == batchDto.getBatchName() || batchDto.getBatchName().trim().isEmpty()){
            String message = String.format("Batch name must not be null and empty");
            throw new BadRequestException(message);
        }

        //checking batch with the name and program id exists
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

            //converting Dto to Entity
            Batch batch = new Batch();
            batch.setBatchName(batchDto.getBatchName());
            batch.setBatchDescription(batchDto.getBatchDescription());
            batch.setBatchStatus(batchDto.getBatchStatus());
            batch.setBatchNoOfClasses(batchDto.getBatchNoOfClasses());
            batch.setCreationTime(batchDto.getCreationTime());
            batch.setLastModTime(batchDto.getLastModTime());

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
            returnBatchDto.setCreationTime(saveBatch.getCreationTime());
            returnBatchDto.setLastModTime(saveBatch.getLastModTime());
            return returnBatchDto;
        }
        String message = String.format("Program id does not exist with id '" + batchDto.getProgramId() + "'");
        throw new NotFoundException(message);
    }

    @Override
    public BatchDto updateBatch(int batchId, BatchDto batchDto) {
        LOGGER.info("Updating batch by batchId={}", batchId);

        //checking batch id
        Optional<Batch> optional = batchRepository.findById(batchId);
        if (optional.isPresent()) {
            Batch batch = optional.get();

            // check if programId is not null and if programId is present
            if (null != batchDto.getProgramId()) {
                Optional<Program> programOptional = programRepository.findById(batchDto.getProgramId());
                if (!programOptional.isPresent()) {
                    String message  = String.format("Program with id=%d does not exist",
                            batchDto.getProgramId());
                    throw new BadRequestException(message);
                } else {
                    batch.setProgram(programOptional.get());
                }
            }

            //checking if updating data is not null
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
            returnBatchDto.setCreationTime(saveBatch.getCreationTime());
            returnBatchDto.setLastModTime(saveBatch.getLastModTime());

            return returnBatchDto;
        }
        // throw new BatchNotFoundException();
        String message = String.format("Batch does not exist with id '" + batchId + "'");
        throw new NotFoundException(message);
    }

    @Override
    public ResponseDto deleteBatch(int batchId) {
        LOGGER.info("Deleting batch by batchId={}", batchId);

        //checking batch id
        Optional<Batch> optional = batchRepository.findById(batchId);
        if (optional.isPresent()) {
            Batch batch = optional.get();
            batchRepository.deleteById(batchId);
            return new ResponseDto("Success", "Batch is deleted", new Date());
        }
        //throw new idNotFound
        String message = String.format("Batch does not exist with id '" + batchId + "'");
        throw new NotFoundException(message);
    }

    @Override
    public BatchDto getOneBatch(int batchId) {
        LOGGER.info("Getting batch by batchId={}", batchId);

        Optional<Batch> optional = batchRepository.findById(batchId);
        if(optional.isPresent()){
            Batch batch = optional.get();
            //converting Entity to Dto
            BatchDto batchDto = new BatchDto();
            batchDto.setBatchId(batch.getBatchId());
            batchDto.setBatchName(batch.getBatchName());
            batchDto.setBatchDescription(batch.getBatchDescription());
            batchDto.setBatchStatus(batch.getBatchStatus());
            batchDto.setBatchNoOfClasses(batch.getBatchNoOfClasses());
            batchDto.setCreationTime(batch.getCreationTime());
            batchDto.setLastModTime(batch.getLastModTime());
            batchDto.setProgramId(batch.getProgram().getProgramId());
            return batchDto;
        }
        String message = String.format("Batch does not exist with id '" + batchId + "'");
        throw new NotFoundException(message);
    }
}
