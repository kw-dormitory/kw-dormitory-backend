package com.kw.kwdn.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailDTO {
    private String id;
    private String name;
    private String nickname;
    private String token;
    private String email;
    private String photoUrl;
}
