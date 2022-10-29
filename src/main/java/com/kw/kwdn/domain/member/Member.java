package com.kw.kwdn.domain.member;

import com.kw.kwdn.domain.BaseTimeEntity;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @Column(name = "member_id")
    private String id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "username")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name="photo_url")
    private String photoUrl;

    public MemberDTO toDTO(){
        return MemberDTO.builder()
                .id(id)
                .photoUrl(photoUrl)
                .email(email)
                .nickname(nickname)
                .name(name)
                .token(token)
                .build();
    }
}
