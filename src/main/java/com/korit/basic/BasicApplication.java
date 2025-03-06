package com.korit.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.korit.basic.dto.Lombok;

@SpringBootApplication
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

  void method() {
    Lombok lombok1 = new Lombok("a",1,false,true); // Lombok 안에 괄호 쳐두고 회색 글자로 쓰여진 것. 실제값 아님 IDE가 알려주고 있는 것임.
    Lombok lombok2 = new Lombok("a",1);

    lombok1.getField1();
    lombok1.isField3();
    lombok1.getField4();

    lombok2.setField3(false);
    lombok2.setField4(null);
  }
    
}
