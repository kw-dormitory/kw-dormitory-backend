package com.kw.kwdn.domain.party.controller;

import com.kw.kwdn.domain.IntegrationTest;
import com.kw.kwdn.domain.login.controller.LoginController;
import com.kw.kwdn.domain.party.dto.PartyCreateDTO;
import com.kw.kwdn.domain.party.dto.PartyDetailDTO;
import com.kw.kwdn.domain.party.dto.PartySimpleDTO;
import com.kw.kwdn.domain.party.repository.PartyRepository;
import com.kw.kwdn.domain.party.service.PartyService;
import com.kw.kwdn.domain.security.dto.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class PartyControllerTest extends IntegrationTest {
    @Autowired
    private LoginController loginController;
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private PartyService partyService;

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
    @DisplayName("익명 파티가 성공적으로 생성될 때")
    public void test1() throws Exception {
        // given
        PartyCreateDTO createDTO = PartyCreateDTO.builder()
                .title("같이 밥먹을 사람?")
                .content("제곧내")
                .openTokUrl("http://localhost:8080")
                .build();

        // when
        MvcResult res = mockMvc.perform(post("/api/v1/party/create")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Long saveId = objectMapper.readValue(content, Long.class);
        // 저장된 전체 파티의 개수를 가져오기
        long count = partyRepository.count();

        // then
        assertThat(saveId).isGreaterThan(0L);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("파티 생성 api를 사용했지만 dto의 모든 필드가 채워져 있지 않는 경우")
    public void test2() throws Exception {
        // given, when
        // no title
        PartyCreateDTO createDTO = PartyCreateDTO.builder()
                //.title("같이 밥먹을 사람?")
                .content("제곧내")
                .openTokUrl("http://localhost:8080")
                .build();

        mockMvc.perform(post("/api/v1/party/create")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());


        // no content
        createDTO = PartyCreateDTO.builder()
                .title("같이 밥먹을 사람?")
                //.content("제곧내")
                .openTokUrl("http://localhost:8080")
                .build();

        mockMvc.perform(post("/api/v1/party/create")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());


        // no open tok
        createDTO = PartyCreateDTO.builder()
                .title("같이 밥먹을 사람?")
                .content("제곧내")
                //.openTokUrl("http://localhost:8080")
                .build();

        mockMvc.perform(post("/api/v1/party/create")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());

        long count = partyRepository.count();

        // then
        assertThat(count).isEqualTo(0L);
    }

    @Test
    @DisplayName("파티 하나를 등록하고 해당 파티를 아이디를 이용해서 조회하기")
    public void test3() throws Exception {
        // given
        PartyCreateDTO createDTO = PartyCreateDTO.builder()
                .title("같이 밥먹을 사람?")
                .content("제곧내")
                .openTokUrl("http://localhost:8080")
                .build();

        // when
        MvcResult res = mockMvc.perform(post("/api/v1/party/create")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String content = res.getResponse().getContentAsString();
        Long saveId = objectMapper.readValue(content, Long.class);


        MvcResult res2 = mockMvc.perform(get("/api/v1/party/" + saveId)
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        content = res2.getResponse().getContentAsString();

        PartyDetailDTO dto = objectMapper.readValue(content, PartyDetailDTO.class);

        // then
        assertThat(dto.getTitle()).isEqualTo(createDTO.getTitle());
        assertThat(dto.getContent()).isEqualTo(createDTO.getContent());
        assertThat(dto.getOpenTokUrl()).isEqualTo(createDTO.getOpenTokUrl());
        assertThat(dto.getCreatedAt()).isNotNull();

        assertThat(dto.getCreator()).isNotNull();
        assertThat(dto.getCreator().getNickname()).isEqualTo(userInfo.getNickname());
    }

    @Test
    @DisplayName("pagination을 통한 파티 조회")
    public void test() throws Exception {
        for (int i = 0; i < 20; i++) {
            PartyCreateDTO createDTO = PartyCreateDTO.builder()
                    .title("같이 밥먹을 사람?" + i)
                    .content("제곧내" + i)
                    .openTokUrl("http://localhost:8080")
                    .build();
            partyService.create(createDTO, userInfo.getUserId());
        }

        MvcResult res = mockMvc.perform(post("/api/v1/party")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = res.getResponse().getContentAsString();

        List<PartySimpleDTO> page = List.of(objectMapper.readValue(content, PartySimpleDTO[].class));

        // then
        assertThat(page.size()).isEqualTo(10);
    }
}