package com.kw.kwdn.domain.security.provider;

import com.kw.kwdn.domain.security.MemberDetails;
import com.kw.kwdn.domain.security.token.LoginAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = (String) authentication.getPrincipal();
        if (userId == null) throw new IllegalStateException("userId가 없습니다.");

        log.info("login attempt : " + userId);

        MemberDetails memberDetails = (MemberDetails) userDetailsService.loadUserByUsername(userId);
        LoginAuthenticationToken token = new LoginAuthenticationToken(memberDetails.getId(), memberDetails.getAuthorities());
        log.info("provider finished");
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
