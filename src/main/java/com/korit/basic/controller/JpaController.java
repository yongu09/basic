package com.korit.basic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korit.basic.service.JpaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor // 필수요소에 대해서만 생성자를 만들겠다
public class JpaController {

  private final JpaService jpaService;

  @PostMapping("/sample1")
  public ResponseEntity<String> postSample1() {
    ResponseEntity<String> response = jpaService.createSampleTable1();
    return response;
  }
  
}
