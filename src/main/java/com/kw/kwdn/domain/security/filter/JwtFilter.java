package com.kw.kwdn.domain.security.filter;


import com.kw.kwdn.domain.security.service.JwtSecurityService;
import com.kw.kwdn.domain.security.token.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtSecurityService jwtSecurityService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String token = header.substring("Bearer ".length());
        log.info("jwt token : " + token);

        String subject = jwtSecurityService.getSubject(token);

        if (subject == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Authentication authentication = new JwtAuthenticationToken(subject, subject, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        List<String> canList = List.of("/swagger", "/api/v1/auth", "/v3/api-docs");
        for (String word : canList) if (path.startsWith(word)) return true;
        return !path.startsWith("/api/");
    }
}
