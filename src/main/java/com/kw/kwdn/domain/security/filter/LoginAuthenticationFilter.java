package com.kw.kwdn.domain.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.kwdn.domain.security.dto.UserInfo;
import com.kw.kwdn.domain.security.token.LoginAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;

    @Autowired
    public LoginAuthenticationFilter(ObjectMapper objectMapper) {
        super("/api/v1/login");
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UserInfo userInfo = objectMapper.readValue(request.getReader(), UserInfo.class);
        if (userInfo.getUserId() == null) throw new IllegalArgumentException("there is no userId");

        LoginAuthenticationToken token = new LoginAuthenticationToken(userInfo.getUserId());
        return getAuthenticationManager().authenticate(token);
    }
}
