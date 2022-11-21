package com.kw.kwdn.domain.penalty.controller;

import com.kw.kwdn.domain.IntegrationTest;
import com.kw.kwdn.domain.login.controller.LoginController;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.penalty.PenaltyStatus;
import com.kw.kwdn.domain.penalty.dto.PenaltyItemCreateDTO;
import com.kw.kwdn.domain.penalty.dto.PenaltyStatusDTO;
import com.kw.kwdn.domain.penalty.repository.PenaltyItemRepository;
import com.kw.kwdn.domain.penalty.repository.PenaltyStatusRepository;
import com.kw.kwdn.domain.security.dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class PenaltyControllerTest extends IntegrationTest {
    @Autowired
    private PenaltyItemRepository penaltyItemRepository;
    @Autowired
    private LoginController loginController;

    private String jwtToken;

    @Test
    @DisplayName("8점짜리 벌점 내역을 저장하고 제대로 저장되었는지 확인")
    public void test1() throws Exception {
        // given, when
        String userId = "helloworld1";
        int penalty = 8;

        // login
        UserInfo userInfo = UserInfo.builder()
                .userId(userId)
                .token("token1")
                .build();

        jwtToken = loginController.login(userInfo);

        LocalDate localDate = LocalDate.now();
        String date = localDate.toString();

        // 새로운 벌점을 생성
        PenaltyItemCreateDTO createDTO = PenaltyItemCreateDTO.builder()
                .content("벌점사유")
                .penalty(penalty)
                .date(localDate)
                .build();

        String content = objectMapper.writeValueAsString(createDTO);
        content = content.replace(date, "2022-10-11");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/penalty")
                        .with(user(userId))
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andDo(print())
                .andExpect(status().isOk());

        long count = penaltyItemRepository.count();
        assertThat(count).isEqualTo(1L);

        // 저장된 값들을 불러오기
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/penalty")
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        PenaltyStatusDTO dto = objectMapper.readValue(res.getResponse().getContentAsByteArray(), PenaltyStatusDTO.class);

        assertThat(dto.getTotalPenalty()).isEqualTo(8);
        assertThat(dto.getPenaltyItemList().size()).isEqualTo(1);
        assertThat(dto.getPenaltyItemList().get(0).getContent()).isEqualTo("벌점사유");
    }
}