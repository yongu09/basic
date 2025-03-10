package com.korit.basic.entity;

import com.korit.basic.dto.PatchUserRequestDto;
import com.korit.basic.dto.PostUserRequestDto;
import com.korit.basic.dto.SignUpRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// entity 클래스로 만들거고, entity 명은 user 로 만들길 희망함.
// practice_sql 데이터베이스의 user 테이블과 매핑시킬 것임
@Entity(name="user")
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
  @Id
  private String userId;
  private String userPassword;
  private String userName;
  private String userAddress;
  private String userTelNumber;

  public UserEntity(PostUserRequestDto dto) {
    this.userId = dto.getUserId();
    this.userPassword = dto.getUserPassword();
    this.userName = dto.getUserName();
    this.userAddress = dto.getUserAddress();
    this.userTelNumber = dto.getUserTelNumber();
  }

  public UserEntity(SignUpRequestDto dto) {
    this.userId = dto.getUserId();
    this.userPassword = dto.getUserPassword();
    this.userName = dto.getUserName();
    this.userAddress = dto.getUserAddress();
    this.userTelNumber = dto.getUserTelNumber();
  }

  public void patch(PatchUserRequestDto dto) {
    this.userName = dto.getUserName();
    this.userAddress = dto.getUserAddress();
  }
}