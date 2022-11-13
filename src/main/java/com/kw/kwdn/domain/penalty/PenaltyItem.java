package com.kw.kwdn.domain.penalty;


import com.kw.kwdn.domain.penalty.dto.PenaltyItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public PenaltyItemDTO toDTO() {
        return PenaltyItemDTO.builder()
                .id(id)
                .content(content)
                .penalty(penalty)
                .createdAt(createdAt)
                .build();
    }

    //** domain logic **//
    public void updatePenaltyStatus(PenaltyStatus penaltyStatus){
        this.penaltyStatus = penaltyStatus;
    }
}
