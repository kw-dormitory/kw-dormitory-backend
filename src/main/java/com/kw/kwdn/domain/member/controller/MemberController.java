package com.kw.kwdn.domain.member.controller;

import com.kw.kwdn.domain.member.dto.MemberDetailDTO;
import com.kw.kwdn.domain.member.dto.MemberSettingDTO;
import com.kw.kwdn.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/detail")
    public MemberDetailDTO detail(Principal principal) {
        String memberId = principal.getName();
        return memberService.findDetailById(memberId);
    }

    @GetMapping("/setting")
    public MemberSettingDTO findSettingById(Principal principal) {
        String userId = principal.getName();
        return memberService.findSettingById(userId);
    }
}
