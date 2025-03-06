package com.korit.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Lombok 라이브러리
// - getter, setter, constructor 등 자주 사용되지만 반복적으로 작성해야하는 코드를 자동으로 생성해주는 라이브러리이다.
// - 프로젝트가 lombok 라이브러리 의존성을 가지고 있어야 한다.
// - 대부분의 IDE는 기본적으로 Lombok 어노테이션에 대해 이해를 하지 못하기 때문에 IDE에도 Lombok 추가 확장이 필요하다.

// @Getter: getter 메서드 생성
// @Setter: setter 메서드 생성
// @ToString: toString 메서드 생성 (필드명과 값을 문자열로 표현해준다.)
// @NoArgsConstructor: 기본 생성자 생성 (빈 생성자_매개변수 아무것도 없는)
// @AllArgsConstructor: 모든 멤버 변수를 매개변수로 받는 생성자 생성 
// @RequiredArgsConstructor: 필수 멤버 변수를 매개변수로 받는 생성자 생성 
// @NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Lombok {
  private final String field1;
  private final int field2;
  private boolean field3;  // 기본형 boolean으로 만든 - 여기서 getter 메서드로 쓸 때 is 써야함 get이 아닌
  private Boolean field4;  // 참조형 Boolean으로 만든

}
