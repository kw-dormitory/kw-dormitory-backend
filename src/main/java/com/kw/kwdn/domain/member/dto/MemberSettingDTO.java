package com.kw.kwdn.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSettingDTO {
    private String id;
    private Boolean curfew;
    private Boolean regular;
    private Boolean notice;
}
