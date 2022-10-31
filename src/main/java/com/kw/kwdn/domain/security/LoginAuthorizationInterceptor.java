package com.kw.kwdn.domain.security;

import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.security.token.LoginAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthorizationInterceptor implements HandlerInterceptor {
    private final MemberRepository memberRepository;
    private String BEARER = "Bearer ";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler)
            throws Exception {
        String header = request.getHeader("Authorization");
        if (header == null) throw new IllegalArgumentException("authorization token 값이 없습니다.");
        String token = header.substring(BEARER.length());
        boolean isExist = memberRepository.existsById(token);
        if (!isExist) {
            log.warn("존재하지 않는 사용자 토큰값(" + token + ")으로 접속하려고 합니다.");
            response.setStatus(HttpStatus.SC_FORBIDDEN);
        }
        Authentication authentication = new LoginAuthenticationToken(token, token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return isExist;
    }
}
