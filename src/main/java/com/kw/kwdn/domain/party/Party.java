package com.kw.kwdn.domain.party;

import com.kw.kwdn.domain.BaseTimeEntity;
import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.party.dto.PartySimpleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "party")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Party extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "party_id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "open_tok_url", nullable = false)
    private String openTokUrl;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member creator;

    public PartySimpleDTO toSimpleDTO(){
        return PartySimpleDTO.builder()
                .id(id)
                .title(title)
                .content(content)
                .openTokUrl(openTokUrl)
                .createdAt(createdAt)
                .build();
    }
}
