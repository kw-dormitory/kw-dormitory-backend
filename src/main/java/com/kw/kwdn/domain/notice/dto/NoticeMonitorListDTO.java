package com.kw.kwdn.domain.notice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeMonitorListDTO {
    private List<NoticeListItemDTO> list = new ArrayList<>();
    private List<NoticeListItemDTO> topList = new ArrayList<>();
    private List<TotalCountDTO> totalCount = new ArrayList<>();
}
