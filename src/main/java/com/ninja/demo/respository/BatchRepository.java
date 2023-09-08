package com.ninja.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ninja.demo.entity.Batch;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer>{

}
