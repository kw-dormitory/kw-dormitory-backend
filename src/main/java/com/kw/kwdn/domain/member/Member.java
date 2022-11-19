package com.kw.kwdn.domain.member;

import com.kw.kwdn.domain.BaseTimeEntity;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.dto.MemberDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "fcm_token", nullable = false)
    private String token;


    // convert logic
    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(id)
                .token(token)
                .build();
    }

    public MemberDetailDTO toDetailDTO() {
        return MemberDetailDTO.builder()
                .id(id)
                .token(token)
                .build();
    }

    // domain logic
    public void updateToken(String token) {
        this.token = token;
    }
}
