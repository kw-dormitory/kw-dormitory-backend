package com.kw.kwdn.domain.penalty.controller;

import com.kw.kwdn.domain.penalty.dto.PenaltyItemCreateDTO;
import com.kw.kwdn.domain.penalty.dto.PenaltyItemDTO;
import com.kw.kwdn.domain.penalty.dto.PenaltyStatusDTO;
import com.kw.kwdn.domain.penalty.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/penalty")
public class PenaltyController {
    private final PenaltyService penaltyService;

    @GetMapping("")
    public PenaltyStatusDTO findMyPenaltyStatus(Principal principal) {
        String userId = principal.getName();
        return penaltyService.findMyPenaltyStatus(userId);
    }

    @GetMapping("/item")
    public List<PenaltyItemDTO> findMyPenaltyItemList(Principal principal){
        return penaltyService.findMyPenaltyItemList(principal.getName());
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
