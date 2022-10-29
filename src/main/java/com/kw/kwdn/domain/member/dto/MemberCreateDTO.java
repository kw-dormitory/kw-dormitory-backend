package com.kw.kwdn.domain.member.dto;

import com.kw.kwdn.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberCreateDTO {
    private String id;
    private String nickname;
    private String token;
    private String photoUrl;
    private String name;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .photoUrl(photoUrl)
                .token(token)
                .build();
    }
}
