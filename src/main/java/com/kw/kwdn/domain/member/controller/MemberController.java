package com.kw.kwdn.domain.member.controller;

import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.dto.MemberDetailDTO;
import com.kw.kwdn.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/profile/image/upload")
    public String uploadProfileImage(
            @RequestParam(name = "image") List<MultipartFile> files,
            Principal principal
    ) {
        if (files == null || files.isEmpty())
            throw new IllegalArgumentException("적어도 하나 이상의 파일을 입력으로 넣어야합니다.");
        return memberService.uploadProfileImage(principal.getName(), files.get(0));
    }

    @GetMapping("")
    public MemberDetailDTO detail(Principal principal) {
        String memberId = principal.getName();
        return memberService.findDetailById(memberId);
    }
}
