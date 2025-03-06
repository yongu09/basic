package com.korit.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


// Controller 레이어: 
// - 클라이언트와 서버간의 접점에 해당
// - 클라이언트의 요청을 받고 해당 요청에 대한 응답을 처리하는 공간
// - 각 요청의 HTTP method와 url에 해당하는 메서드르 작성하는 공간

// @RestController: JSON 형태의 ResponseBody를 반환하는 Controller임을 명시하는 어노테이션이다.
// @Controller + @ResponseBody 한 것이다.
// @Controller : 해당 클래스가 HTML 형태의 ResponseBody를 반환하는 Controller임을 명시하는 어노테이션이다.
// @ResponseBody : 컨트롤러의 응답형태를 직접 조작할 수 있도록 하는 어노테이션이다.
@RestController
// @RequestMapping : HTTP 요청에 클래스 혹은 메서드를 URL 및 HTTP Method 등으로 매핑하기 위한 어노테이션이다.
// HTTP GET 127.0.0.1:8080/basic/** (~basic 으로 요청한 것을 다 받아준다는)
// @RequestMapping(value="/basic", method=RequestMethod.GET)
@RequestMapping("/basic")
public class BasicController {
  
  // GET http://localhost:8080/basic/first
  @RequestMapping(value="/first", method=RequestMethod.GET)
  public String firstMethod() {
    return "Spring Boot 첫번째 메소드";
  }

  // @GetMapping : RequestMapping을 GET method에 한정시킨 어노테이션
  // GET method - 클라이언트가 데이터를 받기 위한 메소드
  // Request Body 가 존재하지 않음 / Response Body 는 존재함 / HTML form 에서 사용가능함
  @GetMapping("")
  public String getMethod() {
      return "GET Method";
  }
  
  // @PostMapping : RequestMapping을 POST에 한정시킨 어노테이션
  // POST method - 클라이언트가 서버에 데이터를 전송하기위한 메소드
  //             - 클라이언트가 서버에 데이터를 작성하기위한 메소드 (CREATE에 한정)
  // Request Body 가 존재함  / Response Body 가 존재함  / HTML form 양식도 존재(사용가능)
  @PostMapping("")
  public String postMethod() {
    return "POST Method";
  }

  // @PutMapping : RequestMapping을 PUT에 한정시킨 어노테이션
  // PUT method - 클라이언트가 서버에 데이터를 생성 혹은 수정하기위한 메소드
  //            - 만약 전송한 데이터가 동일한 데이터가 있다면 수정
  //            - 만약 전송한 데이터가 존재하지 않는 데이터면 생성
  //            - 동일한 데이터를 여러번 전송하더라도 한번만 적용
  // Request Body 존재함 / Response Body 는 존재하지 않음 / HTML form 은 사용할 수 없음
  @PutMapping("")
  public String putMethod() {
    return "PUT Method";
  }

  // @PatchMapping : RequestMapping을 PATCH에 한정시킨 어노테이션
  // PATCH method - 클라이언트가 서버의 일부 데이터를 수정하기 위한 메소드
  // Request Body 존재함 / Response Body 존재함 / HTML form 은 존재하지 않음
  @PatchMapping("")
  public String patchMethod() {
    return "PATCH Method";
  }

  // @DeleteMapping : RequestMapping을 DELETE에 한정시킨 어노테이션
  // DELETE Method - 클라이언트가 서버에 데이터를 삭제하기 위한 메소드
  // RequestBody 가 있을수도 없을수도 있으나 일단 없는 / ResponseBody 도 있을수도 없을수도 maybe임 / HTML form 존재하지 않음
  @DeleteMapping("")
  public String deleteMethod() {
    return "DELETE Method";
  }

  // ! 주의 HTTP Method + URL 패턴이 중복되면 런타임 에러가 발생함
  // @GetMapping("/duplicate")
  // public void duplicate1() {}

  // @GetMapping("/duplicate")
  // public void duplicate2() {}
}
