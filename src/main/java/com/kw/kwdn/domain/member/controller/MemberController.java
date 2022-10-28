package com.kw.kwdn.domain.member.controller;

import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String join(@RequestBody MemberCreateDTO dto){
        return memberService.join(dto);
    }
}
