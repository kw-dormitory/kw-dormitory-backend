package com.kw.kwdn.domain.notice.controller;

import com.kw.kwdn.domain.notice.dto.NoticeDetailsDTO;
import com.kw.kwdn.domain.notice.dto.NoticeListItemDTO;
import com.kw.kwdn.domain.notice.dto.NoticeMonitorDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/test")
    public void test() {
        NoticeMonitorDTO notice = noticeService.getNotice(0L, 10L);
        System.out.println(notice);
    }

    @PostMapping("")
    public List<NoticeListItemDTO> pagination(
            @RequestParam(value = "page", defaultValue = "1") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return noticeService.convertNoticeList(page, size);
    }

    @PostMapping("/{noticeId}")
    public NoticeDetailsDTO getNoticeDetails(
            @PathVariable(name = "noticeId") String noticeId
    ) {
        return noticeService.getNoticeDetails(noticeId);
    }
}
