package com.kw.kwdn.domain.notification.tasklet;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import com.kw.kwdn.domain.notice.dto.NoticeCreateDTO;
import com.kw.kwdn.domain.notice.dto.NoticeListDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonAndRegularNoticeTasklet implements Tasklet {
    private final NoticeService noticeService;
    private final FirebaseService firebaseService;
    private String commonTitle = "[광운대 기숙사] 새로운 공지사항이 있어요!";
    private String regularTitle = "[광운대 기숙사] 새로운 상시모집관련 공지사항이 있어요!";

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext)
            throws Exception {
        List<NoticeListDTO> noticeList = noticeService.getNotice(1L, 10L);

        Map<Boolean, List<NoticeListDTO>> notExistListGroupingByTitle = noticeList.stream()
                .filter(notice -> !noticeService.existById(notice.getNoticeId()))
                .collect(groupingBy(notice -> notice.getTitle().contains("상시모집")));

        // 상시 모집 또는 일반 공지사항이 null 인 경우를 위한 사전 초기화
        notExistListGroupingByTitle.putIfAbsent(true, new ArrayList<>());
        notExistListGroupingByTitle.putIfAbsent(false, new ArrayList<>());

        System.out.println(notExistListGroupingByTitle);

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

        return RepeatStatus.FINISHED;
    }
}