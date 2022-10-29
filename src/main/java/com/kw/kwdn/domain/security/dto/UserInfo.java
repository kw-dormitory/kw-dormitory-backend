package com.kw.kwdn.domain.security.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String userId;
    private String email;
    private String name;
    private String token;
    private String nickname;
    private String photoUrl;
}
