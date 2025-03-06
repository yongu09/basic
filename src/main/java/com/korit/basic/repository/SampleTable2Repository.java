package com.korit.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.basic.entity.SampleTable2Entity;

@Repository
public interface SampleTable2Repository 
extends JpaRepository<SampleTable2Entity, Integer> {
  
}
