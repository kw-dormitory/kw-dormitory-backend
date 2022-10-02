package com.kw.kwdn.domain.anonymousparty.controller;

import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyCreateDTO;
import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyDTO;
import com.kw.kwdn.domain.anonymousparty.service.AnonymousPartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/party")
@RequiredArgsConstructor
public class AnonymousPartyController {
    private final AnonymousPartyService anonymousPartyService;

    @PostMapping("/create")
    public AnonymousPartyDTO create(@RequestBody AnonymousPartyCreateDTO dto){
        return anonymousPartyService.create(dto);
    }
}
