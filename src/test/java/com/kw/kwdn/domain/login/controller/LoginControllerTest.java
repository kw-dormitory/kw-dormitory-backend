package com.kw.kwdn.domain.login.controller;

import com.kw.kwdn.domain.IntegrationTest;
import com.kw.kwdn.domain.member.Member;
import com.kw.kwdn.domain.member.MemberSetting;
import com.kw.kwdn.domain.member.repository.MemberRepository;
import com.kw.kwdn.domain.member.repository.MemberSettingRepository;
import com.kw.kwdn.domain.penalty.PenaltyStatus;
import com.kw.kwdn.domain.penalty.repository.PenaltyStatusRepository;
import com.kw.kwdn.domain.security.dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class LoginControllerTest extends IntegrationTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PenaltyStatusRepository penaltyStatusRepository;
    @Autowired
    private MemberSettingRepository memberSettingRepository;

    @BeforeEach
    public void init() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자가 로그인 요청을 했을 때, 만약 사용자의 정보가 이미 있다면 토큰만 생성하여 반환")
    public void test1() throws Exception {
        String userId = "helloworld1";

        Member member = Member.builder()
                .id(userId)
                .token("token1")
                .build();

        memberRepository.save(member);

        UserInfo userInfo = UserInfo.builder()
                .userId(userId)
                .token("token-1")
                .build();

        String content = objectMapper.writeValueAsString(userInfo);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        long count = memberRepository.count();
        assertThat(count).isEqualTo(1);
        member = memberRepository.findOneById(member.getId()).orElseThrow(() -> new IllegalStateException("해당하는 사용자 정보가 없습니다."));

        // token에 대해서 값이 변경이 되어도 수정이 되는지 확인
        assertThat(member.getId()).isEqualTo(userInfo.getUserId());
        assertThat(member.getToken()).isEqualTo(userInfo.getToken());
    }

    @Test
    @DisplayName("사용자가 로그인 요청을 했을 때, 만약 사용자에 대한 정보가 없으면 사용자를 회원가입시키고 jwt 반환")
    public void test2() throws Exception {
        UserInfo userInfo = UserInfo.builder()
                .token("token1")
                .userId("helloworld1")
                .build();

        String content = objectMapper.writeValueAsString(userInfo);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        // 사용자의 수를 통해서 회원가입이 정상적으로 되었는지 확인
        long count = memberRepository.count();
        assertThat(count).isEqualTo(1);

        Member member = memberRepository
                .findOneById(userInfo.getUserId())
                .orElseThrow(() -> new IllegalStateException("있어야할 사용자가 없습니다."));
        PenaltyStatus penaltyStatus = penaltyStatusRepository.findByUserId(userInfo.getUserId()).get();
        MemberSetting memberSetting = memberSettingRepository.findOneById(userInfo.getUserId()).get();

        // 실제로 값을 조회해서 정상적으로 동작하였는지 확인
        assertThat(member.getId()).isEqualTo(userInfo.getUserId());
        assertThat(member.getToken()).isEqualTo(userInfo.getToken());
        assertThat(penaltyStatus).isNotNull();
        assertThat(memberSetting).isNotNull();
    }

    @Test
    @DisplayName("UserInfo에 필요한 값들이 들어갔는지 validation을 검증")
    public void test3() throws Exception {
        List<UserInfo> userInfoList = List.of(
                UserInfo.builder()
                        //.userId("helloworld1")
                        .token("token1")
                        .build(),
                UserInfo.builder()
                        .userId("helloworld1")
                        //.token("token1")
                        .build());


        for (UserInfo userInfo : userInfoList) {
            String content = objectMapper.writeValueAsString(userInfo);
            mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(content))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }
}