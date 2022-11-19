package com.kw.kwdn.domain.notice.controller;

import com.kw.kwdn.domain.IntegrationTest;
import com.kw.kwdn.domain.login.controller.LoginController;
import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.notice.dto.NoticeDetailsDTO;
import com.kw.kwdn.domain.notice.dto.NoticeListDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import com.kw.kwdn.domain.security.dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class NoticeControllerTest extends IntegrationTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LoginController loginController;
    @Autowired
    private NoticeService noticeService;

    private String jwtToken;

    @BeforeEach
    public void init() {
        // 한명의 사용자
        Member member = Member.builder()
                .id("uuid1")
                .token("token1")
                .build();
        memberRepository.save(member);

        UserInfo userInfo = UserInfo.builder()
                .userId("uuid1")
                .token("token1")
                .build();

        jwtToken = loginController.login(userInfo);
    }

    @Test
    @DisplayName("사용자가 findAll 요청을 보낼 때, 모든 notice 정보들이 조회되어야한다.")
    public void test1() throws Exception {
        // when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notice/all")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        List<NoticeListDTO> list = List.of(objectMapper.readValue(body, NoticeListDTO[].class));

        // then
        Long total = noticeService.getTotalSize();
        assertThat(total).isEqualTo(list.size());
    }

    @Test
    @DisplayName("size가 15인 5번째 공지 목록을 가지고 오는 테스트")
    public void test2() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notice")
                        .queryParam("page", "5")
                        .queryParam("size", "15")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        List<NoticeListDTO> list = List.of(objectMapper.readValue(body, NoticeListDTO[].class));

        // then
        Integer noticeSize = list.size();
        assertThat(noticeSize).isEqualTo(15);
    }

    @Test
    @DisplayName("아무 쿼리를 주지 않았을 때 정상적으로 page=1, size=10인 공지 사항을 받아오는지 확인")
    public void test3() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notice")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        List<NoticeListDTO> list = List.of(objectMapper.readValue(body, NoticeListDTO[].class));

        // then
        Integer noticeSize = list.size();
        assertThat(noticeSize).isEqualTo(10);
    }

    @Test
    @DisplayName("가장 최신의 공지를  10개 받아오기")
    public void test4() throws Exception {
        // given, when
        // 가장 최근 공지사항 10개를 불러오는 로직
        MvcResult noticeListResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notice")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String body = noticeListResult.getResponse().getContentAsString();
        List<NoticeListDTO> list = List.of(objectMapper.readValue(body, NoticeListDTO[].class));

        // then
        Integer noticeSize = list.size();
        assertThat(noticeSize).isEqualTo(10);
    }
}