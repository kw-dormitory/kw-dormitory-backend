package com.kw.kwdn.domain.penalty.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyStatusDTO {
    private Integer totalPenalty;
    private List<PenaltyItemDTO> penaltyItemList;
}
