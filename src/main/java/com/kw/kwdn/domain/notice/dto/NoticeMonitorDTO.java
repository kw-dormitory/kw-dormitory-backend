package com.kw.kwdn.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeMonitorDTO {
    private List<NoticeMonitorListDTO> root = new ArrayList<>();
}
