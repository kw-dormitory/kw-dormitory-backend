package com.kw.kwdn.domain.party.service;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.party.Party;
import com.kw.kwdn.domain.party.dto.PartyCreateDTO;
import com.kw.kwdn.domain.party.dto.PartyDetailDTO;
import com.kw.kwdn.domain.party.repository.PartyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PartyServiceTest {

    @Mock
    private PartyRepository partyRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private PartyService partyService;

    private Member createMember(String memberId){
        return Member.builder()
                .id(memberId)
                .token("token1")
                .build();
    }

    private Party createParty(Long partyId, Member member){
        return Party.builder()
                .id(partyId)
                .title("title1")
                .content("content")
                .openTokUrl("url")
                .creator(member)
                .build();
    }

    @Test
    @DisplayName("하나의 파티 조회를 성공할 때")
    public void test1() throws Exception {
        // given
        Long partyId = 1L;

        Member member = createMember("userId1");

        Party party = createParty(12345L, member);

        given(partyRepository.findById(partyId)).willReturn(Optional.of(party));

        // when
        PartyDetailDTO dto = partyService.findOneById(partyId);

        // then
        assertThat(dto.getTitle()).isEqualTo(party.getTitle());
        assertThat(dto.getContent()).isEqualTo(party.getContent());
        assertThat(dto.getOpenTokUrl()).isEqualTo(party.getOpenTokUrl());

        // verify
        verify(partyRepository, times(1)).findById(partyId);
    }

    @Test
    @DisplayName("하나의 파티를 성공적으로 생성해서 저장된 party id 값을 반환")
    public void test2() throws Exception {
        // given
        String userId = "userId1";
        PartyCreateDTO dto = PartyCreateDTO.builder()
                .content("content1")
                .openTokUrl("url1")
                .title("title1")
                .build();

        Member member = createMember(userId);

        Party party =createParty(1234L, member);

        given(partyRepository.save(any())).willReturn(party);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        Long savedId = partyService.create(dto, userId);

        // then
        assertThat(savedId).isNotNull();

        // verify
        verify(partyRepository, times(1)).save(any());
    }
}