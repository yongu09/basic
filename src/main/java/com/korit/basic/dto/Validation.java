package com.korit.basic.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// client request body 데이터 유효성 검사
// - spring-boot-starter-validation 의존성 사용해야함
// - 스프링 프레임워크에서 제공하는 유효성 검사 인터페이스 라이브러리이다.
// - 클라이언트가 서버로 데이터를 전송할 때 유효한 데이터일 때만 서버에 데이터를 전송할 수 있도록 도움을 준다.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Validation {
  
  // @NotNull: null을 허용하지 않음
  @NotNull
  private String notNull;

  // 기본형 데이터타입은 @NotNull로 필수 지정 못함
  // Wrapper 타입으로 지정해줘야 한다.
  @NotNull
  private Integer intNotNull;

  // @NotEmpty: 문자열에서 null과 빈 문자열을 허용하지 않음
  @NotEmpty
  private String notEmpty;

  // @NotBlank: 문자열에서 null과 빈문자열 혹은 공백문자열을 허용하지 않음
  @NotBlank
  private String notBlank;

  // !주의!: NotBlank와 NotEmpty를 문자열이 아닌 타입에서 사용하지 않도록 해야함
  // @NotBlank
  // private Integer intNotBlank;

  // @Length(min=?, max=?): 문자열 타입에서 길이의 최소/최대를 검사할 수 있음
  // null에 대한 검사는 수행하지 않음
  @Length(min=3, max=8)
  @NotNull // 같이 걸어서 null 검사도 하도록 하는
  private String length;

  // @Size(min=?, max=?) : 컬렉션에서 길이의 최소 최대를 검사할 수 있음
  @Size(min=3, max=8)
  private List<Integer> size;

  // @Min(), @Max(): 정수의 최소/최대값을 검사할 수 있음 
  @Min(5)
  @Max(15)
  private Integer minMax;

  // @Range(min=?, max=?): 정수의 최소/최대값을 검사할 수 있음
  @Range(min=5, max=15)
  private Integer range;

  // @Pattern(regexp=정규식): 정규식을 이용해서 문자열의 패턴을 검사할 수 있음
  @Pattern(regexp="^[a-zA-Z]*$")
  private String pattern;

}
