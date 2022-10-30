package com.kw.kwdn.domain.party.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyCreateDTO {
    private String userId;
    private String title;
    private String openTokUrl;
    private String content;
}
