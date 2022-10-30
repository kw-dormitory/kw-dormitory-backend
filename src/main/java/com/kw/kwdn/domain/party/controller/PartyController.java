package com.kw.kwdn.domain.party.controller;

import com.kw.kwdn.domain.party.dto.PartySearch;
import com.kw.kwdn.domain.party.dto.PartyCreateDTO;
import com.kw.kwdn.domain.party.dto.PartyDetailDTO;
import com.kw.kwdn.domain.party.dto.PartySimpleDTO;
import com.kw.kwdn.domain.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/party")
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    @PostMapping("/create")
    public Long create(@RequestBody PartyCreateDTO dto) {
        return partyService.create(dto);
    }

    @GetMapping("/{partyId}")
    public PartyDetailDTO detail(@PathVariable(name = "partyId") Long partyId) {
        return partyService.findOneById(partyId);
    }

    @PostMapping("")
    public Page<PartySimpleDTO> findAll(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestBody PartySearch partySearch) {

        return partyService.findAll(PageRequest.of(page, size), partySearch);
    }
}
