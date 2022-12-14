package com.kw.kwdn.domain.login.controller;


import com.kw.kwdn.domain.login.service.LoginService;
import com.kw.kwdn.domain.security.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@Validated @RequestBody UserInfo userInfo) {
        return loginService.login(userInfo);
    }
}
