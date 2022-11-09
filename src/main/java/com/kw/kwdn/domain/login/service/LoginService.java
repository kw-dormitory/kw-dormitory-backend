package com.kw.kwdn.domain.login.service;


import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public String login(UserInfo userInfo) {
        Optional<Member> optMember = memberRepository.findOneById(userInfo.getUserId());

        if (optMember.isEmpty()) {
            // 민액 기존에 사용자 정보가 없으면 회원가입
            Member newMember = userInfo
                    .toCreateDTO()
                    .toEntity();
            memberRepository.save(newMember);
        }else{
            // 사용자 정보가 있으면 토큰 값은 변경하고 반환
            Member member = optMember.get();
            member.updateToken(userInfo.getToken());
        }
        return jwtSecurityService.createToken(userInfo.getUserId(), 1000 * 60 * 60);
    }
}
