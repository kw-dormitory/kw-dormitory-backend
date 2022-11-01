package com.kw.kwdn.domain.notice.dto;

import com.kw.kwdn.domain.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateDTO {
    private Long noticeId;
    private String title;
    private String writer;
    private String createdAt;

    public Notice toEntity() {
        return Notice.builder()
                .noticeId(noticeId)
                .title(title)
                .writer(writer)
                .createdAt(createdAt)
                .build();
    }
}
