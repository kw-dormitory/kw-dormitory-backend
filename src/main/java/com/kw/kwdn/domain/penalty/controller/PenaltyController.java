package com.kw.kwdn.domain.penalty.controller;

import com.kw.kwdn.domain.penalty.dto.PenaltyItemCreateDTO;
import com.kw.kwdn.domain.penalty.dto.PenaltyStatusDTO;
import com.kw.kwdn.domain.penalty.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/penalty")
@RequiredArgsConstructor
public class PenaltyController {
    private final PenaltyService penaltyService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public PenaltyStatusDTO findMyPenaltyStatus(Principal principal) {
        String userId = principal.getName();
        return penaltyService.findMyPenaltyStatus(userId);
    }

    @PostMapping("")
    public Long createPenaltyItem(
            Principal principal,
            @Validated
            @RequestBody
            PenaltyItemCreateDTO dto
    ) {
        String userId = principal.getName();
        return penaltyService.createPenaltyItem(userId, dto);
    }

    @DeleteMapping("/{penaltyId}")
    public Long deletePenaltyItem(
            Principal principal,
            @PathVariable(name = "penaltyId")
            Long penaltyId
    ) {
        String userId = principal.getName();
        return penaltyService.deletePenaltyItem(userId, penaltyId);
    }
}
