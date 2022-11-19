package com.kw.kwdn.domain.security.dto;

import com.kw.kwdn.domain.member.dto.MemberCreateDTO;
import lombok.*;

import javax.validation.constraints.Email;
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
}
