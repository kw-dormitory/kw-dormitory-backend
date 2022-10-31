package com.kw.kwdn.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListDTO {
    private int noticeId;
    private String title;
    private String writer;
    private String createdAt;
}
