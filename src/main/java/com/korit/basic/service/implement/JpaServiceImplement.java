package com.korit.basic.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.basic.entity.SampleTable1Entity;
import com.korit.basic.repository.SampleTable1Repository;
import com.korit.basic.service.JpaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaServiceImplement implements JpaService {

  private final SampleTable1Repository sampleTable1Repository;

  @Override
  public ResponseEntity<String> createSampleTable1() {
    
    SampleTable1Entity entity = new SampleTable1Entity("a", 1, false);

    sampleTable1Repository.save(entity);

    return ResponseEntity.status(HttpStatus.CREATED).body("성공");

  }

}
