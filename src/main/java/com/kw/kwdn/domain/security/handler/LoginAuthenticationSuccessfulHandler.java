package com.kw.kwdn.domain.security.handler;

import com.kw.kwdn.domain.security.dto.UserInfo;
import org.apache.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginAuthenticationSuccessfulHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpStatus.SC_OK);
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        PrintWriter writer = response.getWriter();
        writer.print(userInfo.getUserId());
        writer.close();
    }
}
