package com.ninja.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninja.demo.dto.ProgramDto;
import com.ninja.demo.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer>{

	boolean existsByProgramName(String programName);

	
	

}
