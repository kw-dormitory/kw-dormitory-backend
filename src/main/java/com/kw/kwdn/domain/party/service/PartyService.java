package com.kw.kwdn.domain.party.service;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.party.Party;
import com.kw.kwdn.domain.party.dto.PartyCreateDTO;
import com.kw.kwdn.domain.party.dto.PartyDetailDTO;
import com.kw.kwdn.domain.party.dto.PartySearch;
import com.kw.kwdn.domain.party.dto.PartySimpleDTO;
import com.kw.kwdn.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(PartyCreateDTO dto, String userId) {
        Member member = memberRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));

        Party party = Party.builder()
                .title(dto.getTitle())
                .creator(member)
                .content(dto.getContent())
                .openTokUrl(dto.getOpenTokUrl())
                .build();

        return partyRepository.save(party).getId();
    }

    @Transactional(readOnly = true)
    public PartyDetailDTO findOneById(Long partyId) {
        Party party = partyRepository
                .findById(partyId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 파티가 없습니다."));

        Member creator = party.getCreator();

        PartyDetailDTO dto = PartyDetailDTO
                .builder()
                .title(party.getTitle())
                .content(party.getContent())
                .openTokUrl(party.getOpenTokUrl())
                .createdAt(party.getCreatedAt())
                .build();
        return dto;
    }

    public Page<PartySimpleDTO> findAll(Pageable pageable, PartySearch partySearch) {
        return partyRepository
                .findAll(pageable, partySearch)
                .map(party -> PartySimpleDTO.builder()
                        .id(party.getId())
                        .createdAt(party.getCreatedAt())
                        .title(party.getTitle())
                        .build());
    }

    public List<PartySimpleDTO> findAll() {
        return partyRepository.findAll()
                .stream()
                .map(Party::toSimpleDTO)
                .toList();
    }
}
