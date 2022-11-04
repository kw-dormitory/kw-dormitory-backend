package com.kw.kwdn.domain.login.service;


import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.service.MemberService;
import com.kw.kwdn.domain.security.dto.UserInfo;
import com.kw.kwdn.domain.security.service.JwtSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtSecurityService jwtSecurityService;
    private final MemberService memberService;

    public String login(UserInfo userInfo) {
        Optional<MemberDTO> optMember = memberService.findOneById(userInfo.getUserId());

        if (optMember.isEmpty()) {
            MemberCreateDTO createDTO = MemberCreateDTO.builder()
                    .id(userInfo.getUserId())
                    .token(userInfo.getToken())
                    .email(userInfo.getEmail())
                    .name(userInfo.getName())
                    .nickname(userInfo.getNickname())
                    .photoUrl(userInfo.getPhotoUrl())
                    .build();
            memberService.join(createDTO);
        }

        return jwtSecurityService.createToken(userInfo.getUserId(), 1000 * 60 * 60);
        //return jwtSecurityService.createToken(userInfo.getUserId(), 1000);
    }
}
