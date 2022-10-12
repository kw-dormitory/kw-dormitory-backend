package com.kw.kwdn.domain.notice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.notice.dto.NoticeListItemDTO;
import com.kw.kwdn.domain.notice.dto.NoticeMonitorDTO;
import com.kw.kwdn.domain.notice.dto.NoticeMonitorListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final FirebaseService firebaseService;

    /**
     * https://kw.happydorm.or.kr로 부터 공지 정보를 가지고 오는 메서드
     *
     * @param page 몇번째 페이지를 가지고 올 것인가에 대한 파라미터
     * @param size 한 페이지에 몇개의 공지를 불러올 것인가에 대한 파라미터
     * @author Tianea
     */
    public NoticeMonitorDTO getNotice(Long page, Long size) {
        ResponseEntity<String> res = webClient.post()
                .uri("/bbs/getBbsList.kmc")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromMultipartData("cPage", page)
                        .with("rows", size)
                        .with("bbs_locgbn", "KW")
                        .with("bbs_id", "notice"))
                .retrieve()
                .onStatus(status -> status.value() != 200, r -> Mono.empty())
                .toEntity(String.class)
                .block();
        NoticeMonitorDTO dto = null;
        String body = Objects.requireNonNull(res).getBody();
        try {
            dto = objectMapper.readValue(body, NoticeMonitorDTO.class);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException : NoticeService getNotice");
        }
        return dto;
    }

    /**
     * 외부 서버로 부터 가지고온 공지사항의 depth를 줄이는 메서드
     *
     * @param page 몇번째 페이지를 가지고 올 것인가에 대한 파라미터
     * @param size 한 페이지에 몇개의 공지를 불러올 것인가에 대한 파라미터
     * @author Tianea
     */
    public List<NoticeListItemDTO> convertNoticeList(Long page, Long size) {
        NoticeMonitorDTO notice = this.getNotice(page, size);
        return Optional.of(notice)
                .map(NoticeMonitorDTO::getRoot)
                .map(c -> c.get(0))
                .map(NoticeMonitorListDTO::getList)
                .orElse(new ArrayList<>());
    }

    /**
     * 오후 11시 30분마다 통금 알림을 보내는 스케줄러
     * 크론식을 이용해서 주기적인 스케줄링이 가능하도록 구현
     *
     * @author Tianea
     */
    @Scheduled(cron = "0 30 0 * * *")
    public void curfew() {
        log.info("execute " + LocalDateTime.now());
        firebaseService.sendAlarm("통금시간까지 얼마남지 않았어요", "통금시간까지 30분 남았어요");
    }
}
