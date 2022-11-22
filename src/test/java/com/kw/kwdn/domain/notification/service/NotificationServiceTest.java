package com.kw.kwdn.domain.notification.service;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import com.kw.kwdn.domain.member.MemberSetting;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.repository.MemberSettingRepository;
import com.kw.kwdn.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private MemberService memberService;
    @Mock
    private MemberSettingRepository memberSettingRepository;
    @Mock
    private FirebaseService firebaseService;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    @DisplayName("notice 알림 on, off를 한번찍하고 각각의 의존성이 조회는 2번, 구독과 구독 취소 각각 1번씩 동작하는지 확인")
    public void test1() throws Exception {
        // given
        String userId = "hello world";
        MemberDTO dto = MemberDTO.builder()
                .id(userId)
                .token("token1")
                .build();

        MemberSetting setting = MemberSetting.builder()
                .id(userId)
                .notice(false)
                .curfew(false)
                .regular(false)
                .build();

        given(memberService.findOneById(userId)).willReturn(Optional.ofNullable(dto));
        given(memberSettingRepository.findOneById(userId)).willReturn(Optional.of(setting));

        // when
        // then
        // notice 알림을 킨 경우
        notificationService.noticeAlarmOn(userId);
        assertThat(setting.getNotice()).isEqualTo(true);

        // notice 알림을 끈경우
        notificationService.noticeAlarmOff(userId);
        assertThat(setting.getNotice()).isEqualTo(false);

        //verify
        verify(memberService, times(2)).findOneById(userId);
        verify(memberSettingRepository, times(2)).findOneById(userId);
        verify(firebaseService, times(1)).subscribeToTopic(TopicType.NOTICE, dto.getToken());
        verify(firebaseService, times(1)).unsubscribeToTopic(TopicType.NOTICE, dto.getToken());
    }

    @Test
    @DisplayName("curfew 알림 on, off를 한번찍하고 각각의 의존성이 조회는 2번, 구독과 구독 취소 각각 1번씩 동작하는지 확인")
    public void test2() throws Exception {
        // given
        String userId = "hello world";
        MemberDTO dto = MemberDTO.builder()
                .id(userId)
                .token("token1")
                .build();

        MemberSetting setting = MemberSetting.builder()
                .id(userId)
                .notice(false)
                .curfew(false)
                .regular(false)
                .build();

        given(memberService.findOneById(userId)).willReturn(Optional.ofNullable(dto));
        given(memberSettingRepository.findOneById(userId)).willReturn(Optional.of(setting));

        // when
        // then
        // notice 알림을 킨 경우
        notificationService.curfewAlarmOn(userId);
        assertThat(setting.getCurfew()).isEqualTo(true);

        // notice 알림을 끈경우
        notificationService.curfewAlarmOff(userId);
        assertThat(setting.getCurfew()).isEqualTo(false);

        //verify
        verify(memberService, times(2)).findOneById(userId);
        verify(memberSettingRepository, times(2)).findOneById(userId);
        verify(firebaseService, times(1)).subscribeToTopic(TopicType.CURFEW, dto.getToken());
        verify(firebaseService, times(1)).unsubscribeToTopic(TopicType.CURFEW, dto.getToken());
    }


    @Test
    @DisplayName("regular 알림 on, off를 한번찍하고 각각의 의존성이 조회는 2번, 구독과 구독 취소 각각 1번씩 동작하는지 확인")
    public void test3() throws Exception {
        // given
        String userId = "hello world";
        MemberDTO dto = MemberDTO.builder()
                .id(userId)
                .token("token1")
                .build();

        MemberSetting setting = MemberSetting.builder()
                .id(userId)
                .notice(false)
                .curfew(false)
                .regular(false)
                .build();

        given(memberService.findOneById(userId)).willReturn(Optional.ofNullable(dto));
        given(memberSettingRepository.findOneById(userId)).willReturn(Optional.of(setting));

        // when
        // then
        // notice 알림을 킨 경우
        notificationService.regularRecruitmentAlarmOn(userId);
        assertThat(setting.getRegular()).isEqualTo(true);

        // notice 알림을 끈경우
        notificationService.regularRecruitmentAlarmOff(userId);
        assertThat(setting.getRegular()).isEqualTo(false);

        //verify
        verify(memberService, times(2)).findOneById(userId);
        verify(memberSettingRepository, times(2)).findOneById(userId);
        verify(firebaseService, times(1)).subscribeToTopic(TopicType.REGULAR_RECRUITMENT, dto.getToken());
        verify(firebaseService, times(1)).unsubscribeToTopic(TopicType.REGULAR_RECRUITMENT, dto.getToken());
    }
}