package com.kw.kwdn.domain.notification.service;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseService firebaseService;
    private final MemberService memberService;

    public void noticeAlarmOn(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        firebaseService.subscribeToTopic(TopicType.NOTICE, member.getToken());
    }

    public void noticeAlarmOff(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        firebaseService.unsubscribeToTopic(TopicType.NOTICE, member.getToken());
    }

    public void curfewAlarmOn(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        firebaseService.subscribeToTopic(TopicType.CURFEW, member.getToken());
    }

    public void curfewAlarmOff(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        firebaseService.unsubscribeToTopic(TopicType.CURFEW, member.getToken());
    }

    public void regularRecruitmentAlarmOn(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        firebaseService.subscribeToTopic(TopicType.REGULAR_RECRUITMENT, member.getToken());
    }

    public void regularRecruitmentAlarmOff(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        firebaseService.unsubscribeToTopic(TopicType.REGULAR_RECRUITMENT, member.getToken());
    }
}
