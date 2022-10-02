package com.kw.kwdn.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class NoticeListItemDTO {
    private Long RNUM;
    private String bbsid;
    private String file_view_flag1;
    private String file_view_flag2;
    private String file_view_flag3;
    private String file_view_flag4;
    private String file_view_flag5;
    private String file_name1;
    private String file_name2;
    private String file_name3;
    private String file_name4;
    private String locgbn;
    private String new_flag;
    private String regdate;
    private String regid;
    private String regname;
    private Long reply_cnt;
    private Long seq;
    private String subject;
    private String topflag;
    private String viewpass;
    private Long visitcnt;
}
