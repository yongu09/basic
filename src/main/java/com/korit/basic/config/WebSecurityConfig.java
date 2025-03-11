package com.korit.basic.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.korit.basic.filter.JwtAuthenticationFilter;
import com.korit.basic.handler.OAuth2SuccessHandler;
import com.korit.basic.service.implement.OAuth2UserServiceImplement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final OAuth2UserServiceImplement oAuth2UserService;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  
  // Web Security 설정을 지정하는 메서드
  // @Bean:
  // @Component를 사용하지 못하거나 사용하고 싶지 않을 때 Spring bean으로 등록할 수 있도록 하는 어노테이션이다.
  // WevsecurityConfig 클래스의 인스턴스 생성을 Spring에게 넘기지 않고 configure 메서드 호출만 Spring에게 넘기는 것
  @Bean
  protected SecurityFilterChain configure(HttpSecurity security) throws Exception {
    
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

    // CORS 정책 설정
    .cors(cors -> cors.configurationSource(configurationSource()))

    // 인가 작업 (특정 자원에 대해서 지금 접근하고 있는 접근 주체가 권한이 있느냐 판단하는 작업을 하는)
    // - 요청 URL의 패턴(자원을 의미) 에 따라 인가가 필요한 작업인지 지정하는 설정을 나타냄 (자원에 대한 권한을 지정하는 작업임)
    // - 모든 클라이언트가 접근할 수 있도록 허용 (가장 보안이 낮은)
    // - 인증된 모든 클라이언트가 접근할 수 있도록 허용
    // - 인증된 클라이언트 중 특정 권한을 가진 클라이언트만 접근할 수 있도록 허용 (이렇게 총 3가지 방법이 존재함)  *아래 방법부분이 그나마 이해해둬야 할 부분들.*
    .authorizeHttpRequests(request -> request
      // requestMachers(): URL 패턴, HTTP 메서드, URL 패턴 + HTTP 메서드 마다 접근 권한을 부여하는 메서드
      // permitAll(): 모든 클라이언트가 접근할 수 있도록 지정하는 것
      // authenticated(): 인증된 모든 클라이언트가 접근할 수 있도록 지정
      // hasRole(권한): 특정 권한을 가진 클라이언트만 접근할 수 있도록 지정 (매개변수로 전달하는 권한명은 ROLE_를 제거한 실제 권한명으로 기재함)
      .requestMatchers("/basic", "/basic/**", "/security", "/security/**", "/oauth2/**").permitAll()
      .requestMatchers(HttpMethod.PATCH).authenticated()
      .requestMatchers(HttpMethod.POST, "/user", "/user/**").hasRole("USER")
      // anyRequest(): 나머지 모든 요청에 대한 처리(위에서 지정하지 않은 부분에 대한 처리)
      .anyRequest().authenticated()
    )

    // OAuth2 인증 처리하는 작업
    .oauth2Login(oauth2 -> oauth2
      // 사용자가 oauth2 인증을 위한 요청 URL 지정하는 것
      .authorizationEndpoint(endPoint -> endPoint.baseUri("/security/sns"))
      // oauth2 인증 완료 후 인증서버에서 들어오는 URL을 지정하는 것
      .redirectionEndpoint(endPoint -> endPoint.baseUri("/oauth2/callback/*"))
      // oauth2 인증 완료 후 사용자 정보를 처리할 서비스를 지정하는 것
      .userInfoEndpoint(endPoint -> endPoint.userService(oAuth2UserService))
      // oauth2 서비스 처리 후 성공 시 실행할 기능
      .successHandler(oAuth2SuccessHandler)
    )

    // 인증 및 인가 과정에서 발생한 예외를 직접 처리
    .exceptionHandling(exception -> exception
      .authenticationEntryPoint(new FailAuthenticationEntryPoint())
    )

    // 필터 등록
    // addFilterBefore(추가할 필터 인스턴스, 특정 필터 클래스 객체): 특정 필터 이전에 지정한 필드를 추가
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    // 설정 완료
    return security.build();

  }

  @Bean
  protected CorsConfigurationSource configurationSource() {

    CorsConfiguration configuration = new CorsConfiguration();
    // 출처 지정할 것임
    configuration.addAllowedOrigin("*");
    // HTTP 메서드 지정해줌
    configuration.addAllowedMethod("*");
    // HTTP Request Header 지정해줌
    configuration.addAllowedHeader("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

}

// 인증 및 인가 실패 처리를 위한 커스텀 예외 (AuthenticationEntryPoint 인터페이스 구현)
class FailAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    
    authException.printStackTrace();
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getWriter().write("{\"message\": \"인증 및 인가에 실패했습니다.\"}");

  }

}
