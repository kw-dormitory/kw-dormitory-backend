package com.kw.kwdn.domain.penalty.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyItemDTO {
    private Long id;
    private Integer penalty;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
