package com.korit.basic.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.basic.dto.GetUserResponseDto;
import com.korit.basic.dto.PostUserRequestDto;
import com.korit.basic.dto.ResponseDto;
import com.korit.basic.entity.UserEntity;
import com.korit.basic.repository.UserRepository;
import com.korit.basic.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

  private final UserRepository userRepository;

  public void queryMethod() {
    UserEntity userEntity = new UserEntity();
    
    // save(엔터티): 인스턴스를 레코드로 저장하는 메서드 (수정하는 것이나 삽입하는 것이나 모두 save 사용함)
    // 만약 엔터티의 ID에 해당하는 데이터와 동일한 데이터가 테이블에 존재한다면 수정
    // 만약 엔터티의 ID에 해당하는 데이터와 동일한 데이터가 테이블에 존재하지 않는다면 삽입
    userRepository.save(userEntity);

    // saveAll(엔터티 컬렉션): 컬렉션으로 관리되어지는 엔터티 인스턴스를 모두 저장
    List<UserEntity> entities = new ArrayList<>();
    userRepository.saveAll(entities);

    // findById(아이디 데이터): 아이디를 기준으로 조회할 때 사용
    // - 반환 타입이 Optional 타입으로 반환되기 때문에 불편함
    // - 직접 쿼리 메서드를 작성하는 것이 편함
    userEntity = userRepository.findById("아이디").get();

    // findAll(): 전체 레코드를 조회
    entities = userRepository.findAll();

    // existById(아이디 데이터): 아이디 기준으로 레코드 존재 여부 반환
    boolean existed = userRepository.existsById("아이디");

    // deleteById(아이디 데이터): 아이디 기준으로 레코드 삭제
    userRepository.deleteById("아이디");
    // delete(엔터티): 해당 엔터티 레코드를 삭제
    userRepository.delete(userEntity);
    // deleteAll(엔터티 컬렉션): 해당하는 엔터티 레코드 리스트를 삭제
    userRepository.deleteAll(entities);
  }

  @Override
  public ResponseEntity<ResponseDto> postUser(PostUserRequestDto dto) {
    
    String userId = dto.getUserId();
    String userTelNumber = dto.getUserTelNumber();

    try {
    // UserEntity userEntity = userRepository.findByUserId(userId);
    boolean isExistUserId = userRepository.existsByUserId(userId);
    if (isExistUserId) return ResponseDto.duplicatedId();

    boolean isExistUserTelNumber = userRepository.existsByUserTelNumber(userTelNumber);
    if (isExistUserTelNumber) return ResponseDto.duplicatedTelNumber();

    // UserEntity userEntity = new UserEntity(userId, dto.getUserPassword(), dto.getUserName(), dto.getUserAddress(), userTelNumber);

    // 빌더 패턴: 객체 생성 과정에 멤버변수 별로 객체를 구성한 후 객체를 생성할 수 있도록 도움을 주는 생성 패턴이다.(하나씩 하나씩 집어 넣으면서 만들어지는)
    // 특징: 가독성을 향상, 객체의 불변성을 보장
    // UserEntity userEntity = 
    //   UserEntity
    //     .builder()
    //     .userId(userId)
    //     .userPassword(dto.getUserPassword())
    //     .userName(dto.getUserName())
    //     .userAddress(dto.getUserAddress())
    //     .userTelNumber(userTelNumber)
    //     .build();

    UserEntity userEntity = new UserEntity(dto);
    userRepository.save(userEntity);
  } catch(Exception exception) {
    exception.printStackTrace();
    return ResponseDto.databaseError();
  }

  return ResponseDto.success(HttpStatus.CREATED);

  }

  @Override
  public ResponseEntity<? super GetUserResponseDto> getUser(String userId) {

    UserEntity userEntity = null;

    try {
      userEntity = userRepository.findByUserId(userId);
      if (userEntity == null) return ResponseDto.noExistUser();
    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetUserResponseDto.success(userEntity);

  }

  @Override
  public ResponseEntity<ResponseDto> deleteUser(String userId) {

    try {
      UserEntity userEntity = userRepository.findByUserId(userId);
      if (userEntity == null) return ResponseDto.noExistUser();

    } catch(Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return ResponseDto.success(HttpStatus.OK);
  }

} 