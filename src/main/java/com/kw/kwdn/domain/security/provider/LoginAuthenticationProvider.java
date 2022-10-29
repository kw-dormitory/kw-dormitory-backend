package com.kw.kwdn.domain.security.provider;

import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.service.MemberService;
import com.kw.kwdn.domain.security.dto.MemberDetails;
import com.kw.kwdn.domain.security.dto.UserInfo;
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
    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("provider start");

        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        if (userInfo == null || userInfo.getUserId() == null)
            throw new IllegalArgumentException("there is no userinfo or user id");

        MemberDetails memberDetails = (MemberDetails) userDetailsService.loadUserByUsername(userInfo.getUserId());
        log.info(memberDetails == null ? "null" : memberDetails.toString());

        if (memberDetails == null) {
            MemberCreateDTO dto = MemberCreateDTO.builder()
                    .id(userInfo.getUserId())
                    .token(userInfo.getToken())
                    .name(userInfo.getName())
                    .nickname(userInfo.getNickname())
                    .email(userInfo.getEmail())
                    .photoUrl(userInfo.getPhotoUrl())
                    .build();

            memberService.join(dto);
            memberDetails = (MemberDetails) userDetailsService.loadUserByUsername(userInfo.getUserId());
        }
        LoginAuthenticationToken token = new LoginAuthenticationToken(memberDetails.getUserInfo(), null, memberDetails.getAuthorities());
        log.info("provider end");
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
