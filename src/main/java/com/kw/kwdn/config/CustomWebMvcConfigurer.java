package com.kw.kwdn.config;

import com.kw.kwdn.domain.security.LoginAuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomWebMvcConfigurer extends WebMvcConfigurationSupport {
    private final LoginAuthorizationInterceptor loginAuthorizationInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/login");
    }
}
