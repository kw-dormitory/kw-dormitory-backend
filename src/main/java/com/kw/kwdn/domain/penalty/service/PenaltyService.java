package com.kw.kwdn.domain.penalty.service;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.penalty.PenaltyItem;
import com.kw.kwdn.domain.penalty.PenaltyStatus;
import com.kw.kwdn.domain.penalty.dto.PenaltyItemCreateDTO;
import com.kw.kwdn.domain.penalty.dto.PenaltyStatusDTO;
import com.kw.kwdn.domain.penalty.repository.PenaltyItemRepository;
import com.kw.kwdn.domain.penalty.repository.PenaltyStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {
    private final MemberRepository memberRepository;
    private final PenaltyStatusRepository penaltyStatusRepository;
    private final PenaltyItemRepository penaltyItemRepository;

    @Transactional(readOnly = true)
    public PenaltyStatusDTO findMyPenaltyStatus(String userId) {
        PenaltyStatus status = penaltyStatusRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("해당하는 사용자가 없습니다."));
        return status.toDTO();
    }

    @Transactional
    public Long create(String userId) {
        Member member = memberRepository
                .findOneById(userId)
                .orElseThrow(() -> new IllegalStateException("해당하는 사용자가 없습니다."));

        PenaltyStatus status = PenaltyStatus.create(member);
        return penaltyStatusRepository.save(status).getId();
    }

    @Transactional
    public Long createPenaltyItem(String userId, PenaltyItemCreateDTO dto) {
        PenaltyStatus status = penaltyStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("해당하는 사용자가 없습니다."));

        status.addPenalty(dto.getPenalty());

        PenaltyItem item = dto.toEntity();
        item.updatePenaltyStatus(status);

        return penaltyItemRepository.save(item).getId();
    }

    @Transactional
    public Long deletePenaltyItem(String userId, Long penaltyId) {
        PenaltyItem item = penaltyItemRepository
                .findById(penaltyId)
                .orElseThrow(() -> new IllegalStateException("해당하는 정보가 없습니다."));
        PenaltyStatus status = penaltyStatusRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("해당하는 정보가 없습니다."));

        Member member = status.getMember();
        if (member == null || !member.getId().equals(userId))
            throw new IllegalArgumentException("해당 사용자의 정보를 찾을 수 없거나 다른 사용자의 정보를 삭제할 수 있는 권한이 없습니다.");

        status.subPenalty(item.getPenalty());
        penaltyItemRepository.delete(item);
        return penaltyId;
    }
}
