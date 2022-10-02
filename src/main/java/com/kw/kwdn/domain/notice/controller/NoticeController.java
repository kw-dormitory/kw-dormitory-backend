package com.kw.kwdn.domain.notice.controller;

import com.kw.kwdn.domain.notice.dto.NoticeListItemDTO;
import com.kw.kwdn.domain.notice.dto.NoticeMonitorDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/test")
    public void test(){
        NoticeMonitorDTO notice = noticeService.getNotice(0L, 10L);
        System.out.println(notice);
    }

    @PostMapping("/notice")
    public List<NoticeListItemDTO> pagination(@RequestParam("page") Long page, @RequestParam("size") Long size){
        return noticeService.convertNoticeList(page, size);
    }
}
