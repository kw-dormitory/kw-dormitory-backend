package com.kw.kwdn.domain.notification.controller;

import com.kw.kwdn.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/notice/on")
    public String setNoticeAlarmOn(Principal principal) {
        String userId = principal.getName();
        notificationService.noticeAlarmOn(userId);
        return userId;
    }

    @PostMapping("/notice/off")
    public String setNoticeAlarmOff(Principal principal) {
        String userId = principal.getName();
        notificationService.noticeAlarmOff(userId);
        return userId;
    }

    @PostMapping("/curfew/on")
    public String setCurfewAlarmOn(Principal principal) {
        String userId = principal.getName();
        notificationService.curfewAlarmOn(userId);
        return userId;
    }

    @PostMapping("/curfew/off")
    public String setCurfewAlarmOff(Principal principal) {
        String userId = principal.getName();
        notificationService.curfewAlarmOff(userId);
        return userId;
    }


    @PostMapping("/regular/on")
    public String setRegularRecruitmentAlarmOn(Principal principal) {
        String userId = principal.getName();
        notificationService.regularRecruitmentAlarmOn(userId);
        return userId;
    }

    @PostMapping("/regular/off")
    public String setRegularRecruitmentAlarmOff(Principal principal) {
        String userId = principal.getName();
        notificationService.regularRecruitmentAlarmOff(userId);
        return userId;
    }
}
