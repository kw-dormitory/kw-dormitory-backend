package com.kw.kwdn.domain.member.service;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public String join(MemberCreateDTO dto) {
        Member member = dto.toEntity();
        memberRepository.save(member);
        return member.getId();
    }

    public Optional<MemberDTO> findOneById(String userId) {
        return memberRepository.findOneById(userId).map(Member::toDTO);
    }
}
