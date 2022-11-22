package com.kw.kwdn.config;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.notice.dto.NoticeListDTO;
import com.kw.kwdn.domain.notice.service.NoticeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SchedulingConfigTest {

    @Mock
    private NoticeService noticeService;
    @Mock
    private FirebaseService firebaseService;

    @InjectMocks
    private SchedulingConfig schedulingConfig;

    private String commonTitle = "[광운대 기숙사] 새로운 공지사항이 있어요!";
    private String regularTitle = "[광운대 기숙사] 새로운 상시모집관련 공지사항이 있어요!";


    @Test
    @DisplayName("10개의 공지가 입력으로 들어오고 모든 공지가 이미 DB에 존재한다면 아무일도 일어나지 않는다.")
    public void test1() throws Exception {
        // given
        List<NoticeListDTO> dtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dtos.add(NoticeListDTO
                    .builder()
                    .noticeId((long) i)
                    .build());
        }

        given(noticeService.existById(any())).willReturn(true);
        given(noticeService.getNotice(any(), any())).willReturn(dtos);

        // when
        schedulingConfig.notice();

        // then
        verify(firebaseService, times(0)).sendAlarm(commonTitle, any(), any());
        verify(noticeService, times(10)).existById(any());
    }

    @Test
    @DisplayName("10개의 공지가 입력으로 들어오고 5개의 공지만 DB에 존재한다면 5개에 대한 알림을 요청한다. 그리고 DB에 15개의 notice생성을 요청한다.")
    public void test2() throws Exception {
        // given
        List<NoticeListDTO> dtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dtos.add(NoticeListDTO
                    .builder()
                    .noticeId((long) i)
                    .title("일반모집 공지사항")
                    .build());
        }
        for (int i = 10; i < 15; i++) {
            dtos.add(NoticeListDTO
                    .builder()
                    .noticeId((long) i)
                    .title("상시모집 공지사항")
                    .build());
        }

        given(noticeService.existById(any())).willReturn(false);
        given(noticeService.getNotice(any(), any())).willReturn(dtos);

        // when
        schedulingConfig.notice();

        // then
        verify(noticeService, times(dtos.size())).create(any());
        verify(noticeService, times(dtos.size())).existById(any());
    }


    @Test
    @DisplayName("공지사항을 받아올 수 없다면 예외를 발생시킨다.")
    public void test() throws Exception {
        // given
        given(noticeService.getNotice(any(), any())).willReturn(null);

        // when
        schedulingConfig.notice();

        // then
        verify(firebaseService, times(0)).sendAlarm(any(), any(), any());
        verify(noticeService, times(0)).existById(any());
    }
}