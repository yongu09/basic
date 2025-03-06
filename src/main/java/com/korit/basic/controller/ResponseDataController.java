package com.korit.basic.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/response-data")
public class ResponseDataController {


  @GetMapping("/{name}")
  public ResoponseDto getResponseBody(
    @PathVariable("name") String name
  ) {
    ResoponseDto response = new ResoponseDto(name, new Date());
    return response;
  }

  // ResponseEntity:
  // - Response의 Header, Status code, Status ,essage, Body를 직접 조작할 수 있도록 도와주는 클래스
  // - 메서드의 반환타입을 ResponseEntity로 지정
  // - ResponseEntity의 제너릭을 이용하여 Response Body의 타입을 지정해줘야 한다.
  @GetMapping("/response-entity/{name}")
  public ResponseEntity<ResoponseDto> responseEntity(
    @PathVariable("name") String name
  ) {
    ResoponseDto response = new ResoponseDto(name, new Date());
    // return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
  }
  
}

@Getter
@AllArgsConstructor
class ResoponseDto {
  private String name;
  private Date now;
}
