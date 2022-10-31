package com.kw.kwdn.domain.security;

import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.security.token.LoginAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("my interceptor start");
        String header = request.getHeader("Authorization");
        String token = header.substring(BEARER.length());
        boolean isExist = memberRepository.existsById(token);
        log.info(token + " : " + isExist);

        Authentication authentication = new LoginAuthenticationToken(token, token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return isExist;
    }
}
