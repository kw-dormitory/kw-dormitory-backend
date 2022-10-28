package com.kw.kwdn.domain.security.controller;

import com.kw.kwdn.domain.security.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    @PostMapping("/login")
    public String login(@RequestBody UserInfo userInfo){
        return userInfo.getUserId();
    }
}
