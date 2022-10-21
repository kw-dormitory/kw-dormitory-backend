package com.kw.kwdn.domain.anonymousparty.controller;

import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyCreateDTO;
import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyDTO;
import com.kw.kwdn.domain.anonymousparty.service.AnonymousPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/party")
@RequiredArgsConstructor
public class AnonymousPartyController {
    private final AnonymousPartyService anonymousPartyService;

    @PostMapping("/create")
    public AnonymousPartyDTO create(@RequestBody AnonymousPartyCreateDTO dto) {
        return anonymousPartyService.create(dto);
    }

    @PostMapping("")
    public Page<AnonymousPartyDTO> findAll(
            @RequestParam(name = "page", defaultValue = "10") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return anonymousPartyService.findAll(pageable);
    }
}
