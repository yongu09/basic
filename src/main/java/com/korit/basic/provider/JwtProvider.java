package com.korit.basic.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// JWT:
// - Json Web Token. RFC7519 표준에 정의된 JSON 형식의 문자열을 포함하는 토큰(표준이다)
// - 암호화가 되어 있어 클라이언트와 서버 간 안전한 데이터 전송을 수행할 수 있다
// - JWT 구성으로는 - 헤더: 토큰 유형. 서명에 사용된 암호화 알고리즘이 지정되어 있음
// - 페이로드: 클라이언트 혹은 서버가 전달할 데이터가 포함되어 있음 (실제 데이터가 들어가 있는 위치)
// - 서명: 헤더와 페이로드를 인코딩하여 합치고 비밀키로 암호화한 데이터
@Component
public class JwtProvider {

  // JWT 암호화에 사용되는 비밀키는 보안 관리되어야 함
  // 코드상에 직접 작성하는 것은 보안상 좋지 않음

  // 해결책 (가장 흔하게 사용하는 것이 1번 방법. , 정말 안전하게를 위한다면 3번 방법을 사용하나 비용이 많이 들어 쉽게 사용하기 어려움)
  // 1. application.properties /(또는) application.yaml에 등록해서 사용하는 방법
  // - application.properties / application.yaml에 비밀키를 작성
  // - @Value() 어노테이션을 사용해서 값을 가져옴
  // - 주의사항 : application.properties / application.yaml 을 .gitignore에 등록해야함
  @Value("${jwt.key}")
  private String secretKey;

  // 2. 시스템의 환경 변수로 등록하여 사용
  // - OS 자체의 시스템 환경변수에 비밀키를 등록
  // - Spring에서 시스템 환경변수를 읽어서 사용하는 방법

  // 3. 외부 데이터 관리 도구를 사용
  // - 자체 서버가 아닌 타 서버에 등록된 Vault 도구를 사용하여 비밀키 관리
  // - OS 부팅 시에 Vault 서버에서 비밀키를 가져와 사용
  // - OS 부팅 시 마다 새로운 비밀키를 자동으로 부여함 (장점)

  // JWT 생성 메서드
  public String create(String name) {

    // 비밀키 객체 생성

    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // JWT 만료 시간
    Date expiration = Date.from(Instant.now().plus(9, ChronoUnit.HOURS));
    
    // JWT 생성
    String jwt = Jwts.builder()
      // 서명 알고리즘과 서명에 사용할 키 지정
      .signWith(key, SignatureAlgorithm.HS256)
      // 페이로드 (페이로드 내용 적는)
      // 작성자
      .setSubject(name)
      // 생성시간 (현재시간)
      .setIssuedAt(new Date())
      // 만료시간 (이 있어야 안전하게 만들 수 있는)
      .setExpiration(expiration)
      // 인코딩 (압축)
      .compact();

      return jwt;
  }
}
