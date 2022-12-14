package com.kw.kwdn.domain.notice.controller;

import com.kw.kwdn.domain.notice.dto.NoticeListDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("")
    public List<NoticeListDTO> pagination(
            @RequestParam(value = "page", defaultValue = "1") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
        return noticeService.getNotice(page, size);
    }

    @GetMapping("/all")
    public List<NoticeListDTO> findAll(){
        return noticeService.findAll();
    }
}
