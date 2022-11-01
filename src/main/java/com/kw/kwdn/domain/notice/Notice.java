package com.kw.kwdn.domain.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "notice")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "title")
    private String title;

    @Column(name = "writer")
    private String writer;

    @Column(name = "created_at")
    private String createdAt;
}
