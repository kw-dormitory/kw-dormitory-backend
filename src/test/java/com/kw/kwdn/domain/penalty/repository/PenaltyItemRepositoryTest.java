package com.kw.kwdn.domain.penalty.repository;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.penalty.PenaltyItem;
import com.kw.kwdn.domain.penalty.PenaltyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class PenaltyItemRepositoryTest {

    @Autowired
    private PenaltyItemRepository penaltyItemRepository;
    @Autowired
    private PenaltyStatusRepository penaltyStatusRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void init() {
        // member, penalty status, penalty item의 정보를 모두 초기 세팅한다.
        Member member = Member.builder()
                .id("uuid1")
                .token("token1")
                .build();
        memberRepository.save(member);

        PenaltyStatus status = PenaltyStatus.builder()
                .totalPenalty(0)
                .member(member)
                .build();
        penaltyStatusRepository.save(status);
    }

    @Test
    @DisplayName("penalty item를 user id로 가지고 올때, 불필요한 쿼리 조회가 없는지 확인")
    public void test1() throws Exception {
        // given
        String userId = "uuid1";

        // when
        List<PenaltyItem> res = penaltyItemRepository.findAllByUserId(userId);

        // then
        assertThat(res.size()).isEqualTo(0);
    }
}

