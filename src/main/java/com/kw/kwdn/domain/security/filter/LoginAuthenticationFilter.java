package com.kw.kwdn.domain.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.kwdn.domain.security.dto.UserInfo;
import com.kw.kwdn.domain.security.token.LoginAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;

    @Autowired
    public LoginAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher("/api/v1/login"));
        setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("authentication filter start");
        UserInfo userInfo = objectMapper.readValue(request.getReader(), UserInfo.class);
        if (userInfo == null || userInfo.getUserId() == null)
            throw new IllegalArgumentException("there is no userinfo");
        LoginAuthenticationToken token = new LoginAuthenticationToken(userInfo, userInfo);
        log.info("authentication filter end");
        return getAuthenticationManager().authenticate(token);
    }
}
