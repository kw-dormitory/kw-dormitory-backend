package com.kw.kwdn.domain.anonymousparty;

import com.kw.kwdn.domain.BaseTimeEntity;
import com.kw.kwdn.domain.anonymousparty.dto.AnonymousPartyDTO;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity(name = "anonymous_party")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnonymousParty extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anonymous_party_id", nullable = false)
    private Long id;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "creator", nullable = false)
    private String creator;
    @Column(name = "open_tok_url", nullable = false)
    private String openTokUrl;

    public AnonymousPartyDTO toDTO(){
        return AnonymousPartyDTO.builder()
                .id(id)
                .comment(comment)
                .creator(creator)
                .openTokUrl(openTokUrl)
                .build();
    }
}
