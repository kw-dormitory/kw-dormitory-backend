package com.kw.kwdn.domain.member.controller;

import com.kw.kwdn.domain.IntegrationTest;
import com.kw.kwdn.domain.login.controller.LoginController;
import com.kw.kwdn.domain.member.dto.MemberDetailDTO;
import com.kw.kwdn.domain.security.dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MemberControllerTest extends IntegrationTest {

    @Autowired
    private LoginController loginController;

    // login info field
    private String jwt;
    private UserInfo userInfo;

    @BeforeEach
    public void init() {
        userInfo = UserInfo.builder()
                .name("name1")
                .nickname("nickname1")
                .token("token-1")
                .userId("helloworld1")
                .email("test@email.com")
                .build();
        // jwt 토큰 발행
        jwt = loginController.login(userInfo);
    }


    @Test
    @DisplayName("사용자 정보를 불러오려고 했을 때, 정상적인 경우 사용자 정보를 반환")
    public void test1() throws Exception {
        // given, when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/member/detail")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        MemberDetailDTO dto = objectMapper.readValue(content, MemberDetailDTO.class);

        assertThat(dto.getId()).isEqualTo(userInfo.getUserId());
        assertThat(dto.getToken()).isEqualTo(userInfo.getToken());
        assertThat(dto.getName()).isEqualTo(userInfo.getName());
        assertThat(dto.getNickname()).isEqualTo(userInfo.getNickname());
        assertThat(dto.getPhotoUrl()).isEqualTo(userInfo.getPhotoUrl());
        assertThat(dto.getEmail()).isEqualTo(userInfo.getEmail());
    }

    @Test
    @DisplayName("사용자 정보를 불러오려고 했을 때 JWT 토큰이 없는 경우, 403 에러 발생")
    public void test2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/member/detail"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}