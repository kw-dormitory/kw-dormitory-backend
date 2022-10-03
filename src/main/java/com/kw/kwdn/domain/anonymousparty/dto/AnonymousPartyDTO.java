package com.kw.kwdn.domain.anonymousparty.dto;

import com.kw.kwdn.domain.anonymousparty.AnonymousParty;
import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnonymousPartyDTO {
    private Long id;
    private String comment;
    private String creator;
    private String openTokUrl;

    public AnonymousParty toEntity() {
        return AnonymousParty.builder()
                .id(id)
                .creator(getCreator())
                .comment(getComment())
                .openTokUrl(getOpenTokUrl())
                .build();
    }
}
