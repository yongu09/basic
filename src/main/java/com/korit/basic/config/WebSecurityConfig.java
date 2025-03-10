package com.korit.basic.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// Spring Web 보안 설정
// @Configurable:
// - Spring Bean으로 등록되지 않은 클래스에서 @Autowired 를 사용할 수 있도록 하는 어노테이션이다.
@Configurable
// @Configuration:  이게 있어야 @Bean 이 동작할 수 있음
// '메서드'가 호출 시 Spring Bean으로 등록할 수 있도록 하는 어노테이션
@Configuration
// @EnableWebSecurity:
// - Spring Web Security와 관련된 설정을 지원하는 어노테이션이다.
@EnableWebSecurity
public class WebSecurityConfig {
  
  // Web Security 설정을 지정하는 메서드
  // @Bean:
  // @Component를 사용하지 못하거나 사용하고 싶지 않을 때 Spring bean으로 등록할 수 있도록 하는 어노테이션이다.
  // WevsecurityConfig 클래스의 인스턴스 생성을 Spring에게 넘기지 않고 configure 메서드 호출만 Spring에게 넘기는 것
  @Bean
  public SecurityFilterChain configure(HttpSecurity security) throws Exception {
    
    // Class::method (Java는 어느 클래스의 어떤 것을 참조하라는 것)
    // - 메서드 참조. 특정 클래스의 메서드를 참조할 때 사용
    // - 매개변수로 메서드를 전달하고자 할 때 자주 사용 (클래스 내부까지 같이 지정해줘야 하기에 :: 형태로 표시)
    security
    // Basic 인증 박식에 대한 설정
    // Basic 인증 방식 미사용으로 지정
    .httpBasic(HttpBasicConfigurer::disable)

    // Session:
    // 웹 애플리케이션에서 클라이언트에 대한 정보를 유지하기 위한 기술
    // 서버측에서 클라이언트에 대한 정보를 유지하는 방법이다
    // REST API 서버에서는 Session을 유지하지 않음 (지금 만들고 있는 것이 REST API 임) _ 유지하는 것에 의미도 없음. 그래서 아래 STATELESS로 지정해둔 것임

    // Session 유지 방식에 대한 설정
    // 아래는 Session 유지를 하지 않겠다고 지정한 것임
    .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

    // CSRF (Cross-Site Request Forgery)
    // - 클라이언트가 자신의 의도와는 무관하게 공격행위를 하는 것
    // - 흔히 공격자가 정당한 클라이언트의 세션을 탈취하여 공격을 수행함

    // CSRF 취약점에 대한 대비를 설정할 수 있도록 함
    // 여기서는 CSRF 취약점에 대한 대비를 하지 않겠다고 지정할 것임
    .csrf(CsrfConfigurer::disable)
  }
}
