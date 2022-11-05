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
    @Email
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String token;
    private String nickname;
    private String photoUrl;

    public MemberCreateDTO toCreateDTO() {
        return MemberCreateDTO.builder()
                .id(this.userId)
                .token(this.token)
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .photoUrl(this.photoUrl)
                .build();
    }
}
