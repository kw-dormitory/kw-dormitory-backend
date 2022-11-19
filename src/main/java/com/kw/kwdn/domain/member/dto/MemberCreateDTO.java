package com.kw.kwdn.domain.member.dto;

import com.kw.kwdn.domain.member.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDTO {
    private String id;
    private String token;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .token(token)
                .build();
    }
}
