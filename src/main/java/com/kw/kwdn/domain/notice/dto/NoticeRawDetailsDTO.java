package com.kw.kwdn.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticeRawDetailsDTO {
    private String content_type;
    private String contents;
    private String file_view_flag1;
    private String file_view_flag2;
    private String file_view_flag3;
    private String file_view_flag4;
    private String file_view_flag5;
    private String img_view_flag;
    private String locgbn;
    private String ref_seq;
    private String regdate;
    private String regid;
    private String regname;
    private String reply_cnt;
    private String seq;
    private String subject;
    private String top_flag;
    private String viewpass;
    private String visit_cnt;
}
