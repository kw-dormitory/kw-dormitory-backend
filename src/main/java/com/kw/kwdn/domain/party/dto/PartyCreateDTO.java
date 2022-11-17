package com.kw.kwdn.domain.party.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartyCreateDTO {
    @NotNull
    private String title;
    @NotNull
    private String openTokUrl;
    @NotNull
    private String content;
}