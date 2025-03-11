package com.korit.basic.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

// OAuth2 인증에 성공시 인증 서버로부터 사용자의 정보를 받아 저장할 객체
// - 반드시 OAuth2User 인터페이스를 구현해야함
public class CustomOAuth2User implements OAuth2User {

  // name: OAuth2 사용자의 구분자 (id)
  private String name;
  // attribute: OAutu2 사용자의 정보 맵
  private Map<String, Object> attributes;
  // authorities: 사용자의 해당 서버 리소스 접근 권한 리스트
  private Collection<? extends GrantedAuthority> authorities;

  public CustomOAuth2User(String name, Map<String, Object> attributes) {
    this.name = name;
    this.attributes = attributes;
    this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getName() {
    return this.name;
  }
  
}