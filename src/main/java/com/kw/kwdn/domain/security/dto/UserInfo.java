package com.kw.kwdn.domain.security.dto;

import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import com.kw.kwdn.domain.member.dto.MemberUpdateDTO;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @NotNull
    private String userId;
    @NotNull
    private String token;

    public MemberCreateDTO toCreateDTO() {
        return MemberCreateDTO.builder()
                .id(this.userId)
                .token(this.token)
                .build();
    }

    public MemberUpdateDTO toUpdateDTO() {
        return MemberUpdateDTO.builder()
                .id(userId)
                .token(token)
                .build();
    }
}
