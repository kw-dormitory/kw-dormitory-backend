package com.kw.kwdn.domain.member;

import com.kw.kwdn.domain.BaseTimeEntity;
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
    private String username;

    @Column(name = "email")
    private String email;
}
