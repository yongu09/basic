package com.korit.basic.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.korit.basic.dto.SignUpRequestDto;
import com.korit.basic.entity.UserEntity;
import com.korit.basic.repository.UserRepository;
import com.korit.basic.service.SecurityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityServiceImplement implements SecurityService {

  private final UserRepository userRepository;
  // PasswordEncoder 인터페이스:
  // - 비밀번호를 쉽고 안전하게 암호화하여 관리할 수 있도록 도움을 주는 인터페이스이다.
  // - 구현체: BCryptPasswordEncoder, ScryptPasswordEncoder, Pbkdf2PasswordEncoder
  // - String encode(평문비밀번호): 평문 비밀번호를 암호화하여 반환
  // - boolean matches(평문비밀번호, 암호화된 비밀번호): 평문비밀번호와 암호화된 비밀번호가 서로 일치하는지 확인을 해줌 (암호화된 비밀번호가 평문 비밀번호와 일치하는지 확인해주는)
  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public ResponseEntity<String> signUp(SignUpRequestDto dto) {

    try {

      String userId = dto.getUserId();
      boolean isExistUserid = userRepository.existsByUserId(userId);
      if (isExistUserid) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("존재하는 아이디");

      String userTelNumber = dto.getUserTelNumber();
      boolean isExistUserTelNumber = userRepository.existsByUserTelNumber(userTelNumber);
      if (isExistUserTelNumber) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("존재하는 전화번호");

      String userPassword = dto.getUserPassword();
      String encodedPassword = passwordEncoder.encode(userPassword);
      dto.setUserPassword(encodedPassword);

      UserEntity userEntity = new UserEntity(dto);
      userRepository.save(userEntity);

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스 오류");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body("성공");
  }
  
}
