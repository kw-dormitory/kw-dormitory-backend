package com.kw.kwdn.domain.anonymousparty.service;

import com.kw.kwdn.domain.anonymousparty.AnonymousParty;
import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyCreateDTO;
import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyDTO;
import com.kw.kwdn.domain.anonymousparty.repository.AnonymousPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnonymousPartyService {
    private final AnonymousPartyRepository anonymousPartyRepository;

    @Transactional
    public AnonymousPartyDTO create(AnonymousPartyCreateDTO dto){
        return anonymousPartyRepository.save(dto.toEntity()).toDTO();
    }

    @Transactional(readOnly = true)
    public Page<AnonymousPartyDTO> findAll(Pageable pageable){
        return anonymousPartyRepository.findAll(pageable).map(AnonymousParty::toDTO);
    }
}
