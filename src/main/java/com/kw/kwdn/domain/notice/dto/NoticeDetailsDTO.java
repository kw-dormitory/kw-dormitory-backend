package com.kw.kwdn.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailsDTO {
    private String noticeId;
    private String title;
    private String content;
    private String writer;
    private String createdAt;
}
