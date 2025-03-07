package com.korit.basic.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.korit.basic.entity.UserEntity;
import com.korit.basic.provider.JwtProvider;
import com.korit.basic.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// filter:
// - 서버 로직(톰캣의 로직)과 서블릿(컨트롤러부터의 내용) 사이에서 Http Request에 대한 사전 처리를 수행하는 영역
// - filter에서 걸러진 Request는 서블릿까지 도달하지 못하고 중간에서 Reject 된다

@Component
@RequiredArgsConstructor
// OncePerRequestFilter 추상클래스를 확장 구현하여 filter 클래스로 생성 가능
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {

      // 1. request 객체에서 token 가져오기
      String token = getToken(request);
      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      // 2. token 검증
      String subject = jwtProvider.validate(token);
      if (subject == null) {
        return;
      }

      // 3. 데이터베이스에 존재하는 유저인지 확인 (선택 사항에 해당하는 작업)
      UserEntity userEntity = userRepository.findByUserId(subject);
      if (userEntity == null) {
        return;
      }

      // 4. 접근주체의 권한(일반적으로 권한은 리스트 형태로 잡혀있는) 지정
      List<GrantedAuthority> roles = AuthorityUtils.NO_AUTHORITIES;
      // String role = userEntity.getRole();
      // List<GrantedAuthority> roles = new ArrayList<>();
      // roles.add(new SimpleGrantedAuthority(role));

      // 5. principle에 대한 정보를 context에 저장

      // 5-1. principle에 대한 정보를 '인증된 사용자 객체'로 생성(context에 담을 수 있는 형태로 맞춰야)
      // UsernamePasswordAuthenticationToken(사용자 정보, 비밀번호, 권한);
      AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(subject, null, roles);

      // 5-2. 인증 정보에 request 등록
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      // 5-3. 등록할 빈 security context 생성을 해줌
      SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

      // 5-4. 생성한 security context에 인증된 사용자 정보 토큰을 등록
      securityContext.setAuthentication(authenticationToken);

      // 5-5. 생성한 security context를 등록
      SecurityContextHolder.setContext(securityContext);

    } catch(Exception exception) {
      exception.printStackTrace();
    }

    // 6. 다음 필터에 request와 response 객체를 전달
    filterChain.doFilter(request, response);
    
  }
  
  // 1. request 객체 header에서 'Authorization' 필드의 값을 가져옴
  // 2. Authorization 필드 값이 'Bearer'로 시작하는지 확인
  // 3. Authorization 필드 값에서 토큰 추출
  private String getToken(HttpServletRequest request) {
  
    // 1. request 객체 header에서 'Authorization' 필드의 값을 가져옴
    String authorization = request.getHeader("Authorization");

    // - Authorization 필드값이 존재하는지 확인
    boolean hasAuthorization = StringUtils.hasText(authorization);
    if (!hasAuthorization) return null;

    // 2. Authorization 필드 값이 'Bearer'로 시작하는지 확인
    boolean isBearer = authorization.startsWith("Bearer ");
    if (!isBearer) return null; 

    // 3. Authorization 필드 값에서 토큰 추출
    // Bearer salllssasslskjlskfjss
    String token = authorization.substring(7);
    return token;
  }

}
