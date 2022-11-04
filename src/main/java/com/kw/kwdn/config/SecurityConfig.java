package com.kw.kwdn.config;

import com.kw.kwdn.domain.security.filter.JwtFilter;
import com.kw.kwdn.domain.security.handler.JwtAccessDeniedHandler;
import com.kw.kwdn.domain.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        if (config.getAuthenticationManager() == null) {
            log.warn("authentication manager is not exist");
            throw new IllegalStateException("authenticationManager를 생성할 수 없습니다.");
        }
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/api/v1/auth**").permitAll()
                .antMatchers("/api**").authenticated()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/ignore1", "/ignore2");
    }
}
