package com.kw.kwdn.domain.member.dto;

import com.kw.kwdn.domain.member.Member;
import lombok.Data;

@Data
public class MemberCreateDTO {
    private String id;
    private String nickname;
    private String token;
    private String username;
    private String email;

    public Member toEntity(){
        return Member.builder()
                .id(id)
                .email(email)
                .username(username)
                .nickname(nickname)
                .token(token)
                .build();
    }
}
