package com.kw.kwdn.domain.login.controller;


import com.kw.kwdn.domain.security.dto.UserInfo;
import com.kw.kwdn.domain.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@RequestBody UserInfo userInfo) {
        return loginService.login(userInfo);
    }
}
