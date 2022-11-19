package com.kw.kwdn.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSettingDTO {
    private String id;
    private Boolean curfew;
    private Boolean regular;
    private Boolean notice;
}
