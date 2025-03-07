package com.korit.basic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korit.basic.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {
  
  private final JwtProvider jwtProvider;

  @GetMapping("/jwt")
  public String getJwt(
    @RequestParam("name") String name
  ) {
    String jwt = jwtProvider.create(name);
    return jwt;
  }

  @PostMapping("/jwt")
  public String validateJwt(
    @RequestBody String jwt
  ) {
    String subject = jwtProvider.validate(jwt);
    return subject;
  }

}
