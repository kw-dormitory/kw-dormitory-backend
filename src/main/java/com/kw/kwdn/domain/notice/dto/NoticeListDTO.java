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
    private Long noticeId;
    private String title;
    private String writer;
    private String createdAt;

    public NoticeCreateDTO toCreateDTO() {
        return NoticeCreateDTO.builder()
                .noticeId(this.getNoticeId())
                .title(this.getTitle())
                .writer(this.getWriter())
                .createdAt(this.getCreatedAt())
                .build();
    }
}
