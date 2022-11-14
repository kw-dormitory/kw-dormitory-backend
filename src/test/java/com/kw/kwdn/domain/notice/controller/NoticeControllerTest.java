package com.kw.kwdn.domain.notice.controller;

import com.kw.kwdn.domain.IntegrationTest;
import com.kw.kwdn.domain.login.controller.LoginController;
import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
                .name("name1")
                .nickname("nickname1")
                .token("token1")
                .email("email@example.com")
                .photoUrl("photo")
                .build();
        memberRepository.save(member);

        UserInfo userInfo = UserInfo.builder()
                .userId("uuid1")
                .nickname("nickname1")
                .email("email@email.com")
                .name("name1")
                .token("token1")
                .build();

        jwtToken = loginController.login(userInfo);
    }

    @Test
    @DisplayName("사용자가 findAll 요청을 보낼 때")
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
}