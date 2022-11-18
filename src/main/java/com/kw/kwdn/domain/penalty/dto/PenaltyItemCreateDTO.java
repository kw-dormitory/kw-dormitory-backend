package com.kw.kwdn.domain.penalty.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kw.kwdn.domain.penalty.PenaltyItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyItemCreateDTO {
    @NotNull
    private Integer penalty;

    @NotNull
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate createdAt;

    public PenaltyItem toEntity() {
        return PenaltyItem.builder()
                .penalty(this.penalty)
                .content(this.content)
                .createdAt(createdAt)
                .build();
    }
}
