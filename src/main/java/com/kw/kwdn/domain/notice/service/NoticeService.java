package com.kw.kwdn.domain.notice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.kwdn.domain.notice.Notice;
import com.kw.kwdn.domain.notice.dto.*;
import com.kw.kwdn.domain.notice.dto.NoticeRawDetailRootDTO;
import com.kw.kwdn.domain.notice.repository.NoticeRepository;
import com.kw.kwdn.global.error.ErrorComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public List<NoticeListDTO> getNotice(Long page, Long size) {
        // raw data 가져오기
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
        NoticeListRootRawDTO dto = null;
        String body = Objects.requireNonNull(res).getBody();

        try {
            dto = objectMapper.readValue(body, NoticeListRootRawDTO.class);
            log.info(dto.toString());
        } catch (JsonProcessingException e) {
            log.warn(ErrorComment.JSON_PARSE_EXCEPTION.getComment());
            throw new IllegalStateException("NoticeService getNotice : 데이터를 가지고 오는 것에 실패하였습니다.");
        }
        return convertListRawDTOToListDTO(dto);
    }

    private List<NoticeListDTO> convertListRawDTOToListDTO(NoticeListRootRawDTO dto) {
        List<NoticeListRawDTO> noticeListRawDTOS = Optional.of(dto)
                .map(NoticeListRootRawDTO::getRoot)
                .map(rootDto -> rootDto.get(0))
                .map(NoticeListMiddleRawDTO::getList)
                .orElseThrow(()
                        -> new IllegalStateException("NoticeService convertListRawDTOToListDTO : 데이터를 가지고 오는 것에 실패하였습니다."));

        return noticeListRawDTOS.stream()
                .map(raw -> NoticeListDTO.builder()
                        .noticeId(raw.getSeq())
                        .title(raw.getSubject())
                        .writer(raw.getRegname())
                        .createdAt(raw.getRegdate())
                        .build())
                .collect(Collectors.toList());
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
            throw new IllegalStateException("NoticeService getNoticeDetails : 데이터를 가지고 오는 것에 실패하였습니다.");
        }

        if (rootDto.getRoot().isEmpty())
            throw new IllegalStateException("NoticeService getNoticeDetails : 데이터를 가지고 오는 것에 실패하였습니다.");
        NoticeRawDetailsDTO dto = rootDto.getRoot().get(0);

        return NoticeDetailsDTO.builder()
                .noticeId(dto.getSeq())
                .content(dto.getContents())
                .title(dto.getSubject())
                .writer(dto.getRegname())
                .createdAt(
                        dto.getRegdate()
                                .replace("오전 ", "A.M. ")
                                .replace("오후 ", "P.M. "))
                .build();
    }

    @Transactional(readOnly = true)
    public boolean existById(Long noticeId) {
        return noticeRepository.existsById(noticeId);
    }

    @Transactional
    public Long create(NoticeCreateDTO dto) {
        Notice notice = dto.toEntity();
        return noticeRepository.save(notice).getNoticeId();
    }
}
