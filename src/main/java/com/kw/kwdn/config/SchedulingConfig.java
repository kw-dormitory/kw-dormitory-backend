package com.kw.kwdn.config;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import com.kw.kwdn.domain.notice.dto.NoticeCreateDTO;
import com.kw.kwdn.domain.notice.dto.NoticeListDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.groupingBy;

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
        CompletableFuture<LocalDateTime> future = CompletableFuture.supplyAsync(() -> {
            List<NoticeListDTO> noticeList = noticeService.getNotice(1L, 10L);
            if (noticeList == null) {
                log.warn("notice tasklet을 다시 시작해야합니다.");
                throw new IllegalStateException("notice 값을 가지고 올 수 없습니다.");
            }

            Map<Boolean, List<NoticeListDTO>> notExistListGroupingByTitle = noticeList.stream()
                    .filter(notice -> !noticeService.existById(notice.getNoticeId()))
                    .collect(groupingBy(notice -> notice.getTitle().contains("상시모집")));

            // 상시 모집 또는 일반 공지사항이 null 인 경우를 위한 사전 초기화
            notExistListGroupingByTitle.putIfAbsent(true, new ArrayList<>());
            notExistListGroupingByTitle.putIfAbsent(false, new ArrayList<>());

            log.info(String.format("%d개의 새로운 일반 공지와 %d개의 새로운 상시모집 공고가 있습니다.",
                    notExistListGroupingByTitle.get(false).size(),
                    notExistListGroupingByTitle.get(true).size()));

            // 공지사항 내용
            notExistListGroupingByTitle.get(true)
                    .forEach(notice -> {
                        firebaseService.sendAlarm(
                                commonTitle,
                                notice.getTitle(),
                                TopicType.REGULAR_RECRUITMENT);

                        NoticeCreateDTO dto = NoticeCreateDTO.builder()
                                .noticeId(notice.getNoticeId())
                                .title(notice.getTitle())
                                .writer(notice.getWriter())
                                .createdAt(notice.getCreatedAt())
                                .build();

                        noticeService.create(dto);
                    });

            notExistListGroupingByTitle.get(false)
                    .forEach(notice -> {
                        firebaseService.sendAlarm(
                                regularTitle,
                                notice.getTitle(),
                                TopicType.NOTICE);

                        NoticeCreateDTO dto = NoticeCreateDTO.builder()
                                .noticeId(notice.getNoticeId())
                                .title(notice.getTitle())
                                .writer(notice.getWriter())
                                .createdAt(notice.getCreatedAt())
                                .build();

                        noticeService.create(dto);
                    });
            return LocalDateTime.now();
        });
        future.thenAccept((time) -> log.info("notice scheduling을 완료하였습니다. : " + time));
    }
}