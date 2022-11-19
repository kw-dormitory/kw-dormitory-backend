package com.kw.kwdn.domain.member.service;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.MemberSetting;
import com.kw.kwdn.domain.member.dto.*;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.member.repository.MemberSettingRepository;
import com.kw.kwdn.domain.penalty.PenaltyStatus;
import com.kw.kwdn.domain.penalty.repository.PenaltyStatusRepository;
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
    private final MemberSettingRepository memberSettingRepository;
    private final PenaltyStatusRepository penaltyStatusRepository;

    @Transactional
    public void join(MemberCreateDTO dto) {
        Member member = dto.toEntity();
        memberRepository.save(member);

        // penalty status create
        PenaltyStatus penaltyStatus = PenaltyStatus.create(member);
        penaltyStatusRepository.save(penaltyStatus);

        // member setting create
        MemberSetting setting = MemberSetting.create(member.toDTO());
        memberSettingRepository.save(setting);
    }

    @Transactional(readOnly = true)
    public Optional<MemberDTO> findOneById(String userId) {
        return memberRepository.findOneById(userId).map(Member::toDTO);
    }

    @Transactional(readOnly = true)
    public MemberDetailDTO findDetailById(String memberId) {
        return memberRepository
                .findOneById(memberId)
                .map(Member::toDetailDTO)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자 정보가 없습니다."));
    }

    @Transactional(readOnly = true)
    public MemberSettingDTO findSettingById(String userId) {
        return memberSettingRepository
                .findOneById(userId)
                .map(MemberSetting::toDTO)
                .orElseThrow(() -> new IllegalStateException("해당하는 멤버 세팅이 없습니다."));
    }

    public void update(String userId, MemberUpdateDTO updateDTO) {
        if (!userId.equals(updateDTO.getId())) throw new IllegalArgumentException("해당 사용자의 정보를 수정할 권한이 없습니다.");
        Member member = memberRepository.findOneById(userId).orElseThrow(() -> new IllegalStateException("해당하는 사용자가 없습니다."));
        member.updateToken(updateDTO.getToken());
    }
}
