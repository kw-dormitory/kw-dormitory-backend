package com.kw.kwdn.domain.party.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartySimpleDTO {
    private Long partyId;
    private String title;
    private String creatorName;
    private LocalDateTime createdAt;
}
