package com.kw.kwdn.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/user/hello")
    public String userHello() {
        return "user hello";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
