package com.kw.kwdn.domain.party.dto;

import com.kw.kwdn.domain.member.dto.MemberPartyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyDetailDTO {
    private MemberPartyDTO creator;
    private String title;
    private String openTokUrl;
    private String content;
    private LocalDateTime createdAt;
}