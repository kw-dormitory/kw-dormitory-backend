package com.kw.kwdn.domain.notice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kw.kwdn.domain.notice.dto.NoticeListItemDTO;
import com.kw.kwdn.domain.notice.dto.NoticeMonitorDTO;
import com.kw.kwdn.domain.notice.dto.NoticeMonitorListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
public class NoticeService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public NoticeService() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.webClient = WebClient.create("https://kw.happydorm.or.kr");
    }

    // webclient 를 동기적으로 사용
    public NoticeMonitorDTO getNotice(Long page, Long size) {
        // 홈페이지로부터 공지 정보를 가지고 옴
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

    // 기존의 복잡한 DTO 를 풀어서 depth 를 낮춤
    public List<NoticeListItemDTO> convertNoticeList(Long page, Long size) {
        NoticeMonitorDTO notice = this.getNotice(page, size);
        return Optional.of(notice)
                .map(NoticeMonitorDTO::getRoot)
                .map(c -> c.get(0))
                .map(NoticeMonitorListDTO::getList)
                .orElse(new ArrayList<>());
    }
}
