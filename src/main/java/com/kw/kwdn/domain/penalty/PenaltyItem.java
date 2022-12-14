package com.kw.kwdn.domain.penalty;


import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.penalty.dto.PenaltyItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "penalty_item")
public class PenaltyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_item_id")
    private Long id;

    @Column(name = "penalty")
    private Integer penalty;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penalty_status_id")
    private PenaltyStatus penaltyStatus;

    @Column(name = "created_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member creator;

    //** domain logic **//
    public void updatePenaltyStatus(PenaltyStatus penaltyStatus){
        this.penaltyStatus = penaltyStatus;
    }

    public PenaltyItemDTO toDTO() {
        return PenaltyItemDTO.builder()
                .id(id)
                .content(content)
                .penalty(penalty)
                .date(date)
                .build();
    }
}
