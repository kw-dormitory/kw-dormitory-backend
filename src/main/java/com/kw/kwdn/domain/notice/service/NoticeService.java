package com.kw.kwdn.domain.notice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.kwdn.domain.notice.dto.*;
import com.kw.kwdn.domain.notice.dto.NoticeRawDetailRootDTO;
import com.kw.kwdn.global.error.ErrorComment;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class NoticeService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

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
            log.warn(ErrorComment.JSON_PARSE_EXCEPTION.getComment());
            throw new IllegalStateException("데이터를 가지고 오는 것에 실패하였습니다.");
        }
        return dto;
    }

    public List<NoticeListItemDTO> convertNoticeList(Long page, Long size) {
        NoticeMonitorDTO notice = this.getNotice(page, size);
        log.info(notice.toString());
        return Optional.of(notice)
                .map(NoticeMonitorDTO::getRoot)
                .map(c -> c.get(0))
                .map(NoticeMonitorListDTO::getList)
                .orElse(new ArrayList<>());
    }

    public NoticeDetailsDTO getNoticeDetails(String noticeId) {
        ResponseEntity<String> res = webClient.post()
                .uri("/bbs/getBbsView.kmc")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromMultipartData("seq", noticeId)
                        .with("bbs_locgbn", "KW")
                        .with("bbs_id", "notice"))
                .retrieve()
                .onStatus(status -> status.value() != 200, r -> Mono.empty())
                .toEntity(String.class)
                .block();

        String body = Objects.requireNonNull(res).getBody();

        log.info(body);
        NoticeRawDetailRootDTO rootDto = null;
        try {
            rootDto = objectMapper.readValue(body, NoticeRawDetailRootDTO.class);
        } catch (JsonProcessingException e) {
            log.warn(ErrorComment.JSON_PARSE_EXCEPTION.getComment());
            throw new IllegalStateException("데이터를 가지고 오는 것에 실패하였습니다.");
        }

        if (rootDto.getRoot().isEmpty()) throw new IllegalStateException("데이터를 가지고 오는 것에 실패하였습니다.");
        NoticeRawDetailsDTO dto = rootDto.getRoot().get(0);
        NoticeDetailsDTO result = NoticeDetailsDTO.builder()
                .noticeId(dto.getSeq())
                .content(dto.getContents())
                .title(dto.getSubject())
                .writer(dto.getRegname())
                .createdAt(dto.getRegdate())
                .build();
        return result;
    }
}
