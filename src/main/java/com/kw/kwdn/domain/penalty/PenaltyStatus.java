package com.kw.kwdn.domain.penalty;

import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.penalty.dto.PenaltyItemDTO;
import com.kw.kwdn.domain.penalty.dto.PenaltyStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "penalty_status")
public class PenaltyStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "penalty_status_id")
    private Long id;

    @Column(name = "total_penalty", nullable = false)
    private Integer totalPenalty = 0;

    @OneToMany(mappedBy = "penaltyStatus")
    private List<PenaltyItem> penaltyItemList = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    public void addPenalty(Integer newPenalty) {
        this.totalPenalty += newPenalty;
    }

    public void subPenalty(Integer penalty) {
        this.totalPenalty -= penalty;
    }

    public PenaltyStatusDTO toDTO() {
        List<PenaltyItemDTO> itemList = penaltyItemList.stream()
                .map(PenaltyItem::toDTO)
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .toList();

        return PenaltyStatusDTO.builder()
                .totalPenalty(totalPenalty)
                .penaltyItemList(itemList)
                .build();
    }
}