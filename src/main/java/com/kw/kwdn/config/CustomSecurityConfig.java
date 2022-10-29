package com.kw.kwdn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.kwdn.domain.security.filter.LoginAuthenticationFilter;
import com.kw.kwdn.domain.security.handler.LoginAuthenticationEntryPoint;
import com.kw.kwdn.domain.security.handler.LoginAuthenticationSuccessfulHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class CustomSecurityConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        if (config.getAuthenticationManager() == null) log.warn("authentication manager is not exist");
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/api/v1/login").permitAll()
                .anyRequest().permitAll()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .csrf().disable()

                .addFilterBefore(loginAuthenticationFilter(manager), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new LoginAuthenticationEntryPoint());

        return http.build();
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter(AuthenticationManager manager){
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter(manager, objectMapper);
        filter.setAuthenticationManager(manager);
        filter.setAuthenticationSuccessHandler(new LoginAuthenticationSuccessfulHandler());
        return filter;
    }
}
