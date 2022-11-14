package com.kw.kwdn.domain.penalty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.kwdn.domain.login.controller.LoginController;
import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.penalty.PenaltyItem;
import com.kw.kwdn.domain.penalty.PenaltyStatus;
import com.kw.kwdn.domain.penalty.dto.PenaltyItemDTO;
import com.kw.kwdn.domain.penalty.repository.PenaltyItemRepository;
import com.kw.kwdn.domain.penalty.repository.PenaltyStatusRepository;
import com.kw.kwdn.domain.security.dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PenaltyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PenaltyStatusRepository penaltyStatusRepository;
    @Autowired
    private PenaltyItemRepository penaltyItemRepository;
    @Autowired
    private LoginController loginController;
    @Autowired
    private ObjectMapper objectMapper;

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


        // 하나의 penalty status
        PenaltyStatus status = PenaltyStatus.builder()
                .totalPenalty(0)
                .member(member)
                .build();
        penaltyStatusRepository.save(status);

        // 10개의 penalty item
        for (int i = 0; i < 10; i++) {
            PenaltyItem item = PenaltyItem.builder()
                    .penalty(i)
                    .content("content" + i)
                    .createdAt(LocalDateTime.now())
                    .creator(member)
                    .penaltyStatus(status)
                    .build();

            status.addPenalty(i);
            penaltyItemRepository.save(item);
        }

        // 사용자 로그인을 진행
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
    @DisplayName("사용자가 자신의 벌점 리스트를 조회하는 상황")
    public void test1() throws Exception {
        String userId = "uuid1";
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/penalty/item")
                        .with(user(userId))
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        List<PenaltyItemDTO> list = List.of(objectMapper.readValue(
                res.getResponse().getContentAsString(),
                PenaltyItemDTO[].class));

        assertThat(list.size()).isEqualTo(10);
    }
}