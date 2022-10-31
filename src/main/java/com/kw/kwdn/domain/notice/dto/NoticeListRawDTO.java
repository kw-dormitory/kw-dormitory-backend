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
public class NoticeListRawDTO {
    private String RNUM;
    private String bbsid;
    private String file_view_flag1;
    private String file_view_flag2;
    private String file_view_flag3;
    private String file_view_flag4;
    private String file_view_flag5;
    private String locgbn;
    private String new_flag;
    private String regdate;
    private String regid;
    private String regname;
    private String reply_cnt;
    private Integer seq;
    private String subject;
    private String topflag;
    private String viewpass;
    private String visitcnt;
}
