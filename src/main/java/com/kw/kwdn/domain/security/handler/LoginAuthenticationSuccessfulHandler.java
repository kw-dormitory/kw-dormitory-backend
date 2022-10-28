package com.kw.kwdn.domain.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
public class LoginAuthenticationSuccessfulHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.TEXT_PLAIN.getType());

        PrintWriter writer = response.getWriter();
        writer.print(authentication.getPrincipal());
        writer.close();
        log.info("success handler finished");
    }
}
