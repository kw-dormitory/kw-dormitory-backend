package com.kw.kwdn.config;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import com.kw.kwdn.domain.notice.dto.NoticeListDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SchedulingConfig {
    private final NoticeService noticeService;
    private final FirebaseService firebaseService;
    private String commonTitle = "[광운대 기숙사] 새로운 공지사항이 있어요!";
    private String regularTitle = "[광운대 기숙사] 새로운 상시모집관련 공지사항이 있어요!";

    @Scheduled(cron = "0 30 0 * * *")
    public void curfew() {
        CompletableFuture<LocalDateTime> future = CompletableFuture.supplyAsync(() -> {
            String title = "통금시간까지 얼마남지 않았습니다.";
            String body = "(01:00 A.M. 까지 30분 남았습니다.)";
            firebaseService.sendAlarm(title, body, TopicType.CURFEW);
            return LocalDateTime.now();
        });
        future.thenRun(() -> log.info("send curfew successfully" + LocalDateTime.now()));
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void notice() {
        List<NoticeListDTO> noticeList = noticeService.getNotice(1L, 10L);
        if (noticeList == null) {
            log.warn("기숙사 홈페이지로부터 정보를 가지고 올 수 없습니다.");
            return;
        }

        List<NoticeListDTO> notExistNoticeList = noticeList.stream()
                .filter(notice -> !noticeService.existById(notice.getNoticeId()))
                .toList();

        notExistNoticeList.forEach((notice) -> {
            if (notice.getTitle().contains("상시모집"))
                firebaseService.sendAlarm(commonTitle, notice.getTitle(), TopicType.REGULAR_RECRUITMENT);
            else
                firebaseService.sendAlarm(regularTitle, notice.getTitle(), TopicType.NOTICE);
            noticeService.create(notice.toCreateDTO());
        });
    }
}