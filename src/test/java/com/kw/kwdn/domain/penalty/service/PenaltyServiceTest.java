package com.kw.kwdn.domain.penalty.service;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.penalty.PenaltyItem;
import com.kw.kwdn.domain.penalty.PenaltyStatus;
import com.kw.kwdn.domain.penalty.dto.PenaltyItemCreateDTO;
import com.kw.kwdn.domain.penalty.repository.PenaltyItemRepository;
import com.kw.kwdn.domain.penalty.repository.PenaltyStatusRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PenaltyServiceTest {
    @Mock
    private PenaltyStatusRepository penaltyStatusRepository;
    @Mock
    private PenaltyItemRepository penaltyItemRepository;
    @InjectMocks
    private PenaltyService penaltyService;

    @Test
    @DisplayName("새로운 penalty item을 생성할 때 비지니스 로직이 정상적으로 동작하는지 확인하기 위한 로직")
    public void test1() throws Exception {
        // given
        String userId = "hello world";
        PenaltyItemCreateDTO dto = PenaltyItemCreateDTO.builder()
                .content("penalty content1")
                .penalty(10)
                .createdAt(LocalDateTime.now())
                .build();

        PenaltyItem penaltyItem = PenaltyItem.builder().id(23723478L).build();

        PenaltyStatus penaltyStatus = PenaltyStatus.builder()
                .id(354L)
                .totalPenalty(0)
                .build();

        given(penaltyStatusRepository.findByUserId(userId)).willReturn(Optional.ofNullable(penaltyStatus));
        given(penaltyItemRepository.save(any())).willReturn(penaltyItem);

        // when
        Long penaltyItemId = penaltyService.createPenaltyItem(userId, dto);

        // then
        assertThat(penaltyItemId).isEqualTo(penaltyItem.getId());

        verify(penaltyItemRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("penalty item을 삭제하는 비지니스 로직 구현")
    public void test() throws Exception {
        // given
        String userId = "hello world";
        Long penaltyItemId = 794728L;

        PenaltyItem penaltyItem = PenaltyItem.builder()
                .id(penaltyItemId)
                .penalty(4)
                .build();

        PenaltyStatus penaltyStatus = PenaltyStatus.builder()
                .id(354L)
                .member(Member.builder()
                        .id(userId)
                        .build())
                .totalPenalty(10)
                .build();

        given(penaltyItemRepository.findById(penaltyItemId)).willReturn(Optional.ofNullable(penaltyItem));
        given(penaltyStatusRepository.findByUserId(userId)).willReturn(Optional.ofNullable(penaltyStatus));

        // when
        penaltyService.deletePenaltyItem(userId, penaltyItemId);

        // then
        assert penaltyStatus != null;
        assertThat(penaltyStatus.getTotalPenalty()).isEqualTo(6);
        verify(penaltyItemRepository, times(1)).delete(any());
    }
}