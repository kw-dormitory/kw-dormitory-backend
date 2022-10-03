package com.kw.kwdn.domain.anonymousparty.service;

import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyCreateDTO;
import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyDTO;
import com.kw.kwdn.domain.anonymousparty.repository.AnonymousPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnonymousPartyService {
    private final AnonymousPartyRepository anonymousPartyRepository;

    public AnonymousPartyDTO create(AnonymousPartyCreateDTO dto){
        return anonymousPartyRepository.save(dto.toEntity()).toDTO();
    }
}
