package com.kw.kwdn.domain.login.service;


import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.dto.MemberUpdateDTO;
import com.kw.kwdn.domain.member.service.MemberService;
import com.kw.kwdn.domain.penalty.service.PenaltyService;
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
    private final PenaltyService penaltyService;

    public String login(UserInfo userInfo) {
        String userId = userInfo.getUserId();
        Optional<MemberDTO> optionalMemberDTO = memberService.findOneById(userId);
        optionalMemberDTO
                .ifPresentOrElse(
                        // 존재한다면
                        dto -> {
                            MemberUpdateDTO updateDTO = userInfo.toUpdateDTO();
                            memberService.update(userId, updateDTO);
                        },
                        // 존재하지 않는다면
                        () -> {
                            MemberCreateDTO createDTO = userInfo.toCreateDTO();
                            memberService.join(createDTO);
                        });

        return jwtSecurityService.createToken(userInfo.getUserId(), 1000 * 60 * 60 * 24 * 12);
    }
}
