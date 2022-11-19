package com.kw.kwdn.domain.notification.service;

import com.kw.kwdn.domain.firebase.service.FirebaseService;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import com.kw.kwdn.domain.member.MemberSetting;
import com.kw.kwdn.domain.member.dto.MemberDTO;
import com.kw.kwdn.domain.member.repository.MemberSettingRepository;
import com.kw.kwdn.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FirebaseService firebaseService;
    private final MemberService memberService;
    private final MemberSettingRepository memberSettingRepository;

    @Transactional
    public void noticeAlarmOn(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        MemberSetting setting = memberSettingRepository.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member setting이 없습니다."));

        firebaseService.subscribeToTopic(TopicType.NOTICE, member.getToken());
        setting.updateNotice(true);
    }

    @Transactional
    public void noticeAlarmOff(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        MemberSetting setting = memberSettingRepository.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member setting이 없습니다."));

        firebaseService.unsubscribeToTopic(TopicType.NOTICE, member.getToken());
        setting.updateNotice(false);
    }

    @Transactional
    public void curfewAlarmOn(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        MemberSetting setting = memberSettingRepository.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member setting이 없습니다."));

        firebaseService.subscribeToTopic(TopicType.CURFEW, member.getToken());
        setting.updateCurfew(true);
    }

    @Transactional
    public void curfewAlarmOff(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        MemberSetting setting = memberSettingRepository.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member setting이 없습니다."));

        firebaseService.unsubscribeToTopic(TopicType.CURFEW, member.getToken());
        setting.updateCurfew(false);
    }

    @Transactional
    public void regularRecruitmentAlarmOn(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        MemberSetting setting = memberSettingRepository.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member setting이 없습니다."));

        firebaseService.subscribeToTopic(TopicType.REGULAR_RECRUITMENT, member.getToken());
        setting.updateRegular(true);
    }

    @Transactional
    public void regularRecruitmentAlarmOff(String userId) {
        MemberDTO member = memberService.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자가 없습니다."));
        MemberSetting setting = memberSettingRepository.findOneById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 member setting이 없습니다."));

        firebaseService.unsubscribeToTopic(TopicType.REGULAR_RECRUITMENT, member.getToken());
        setting.updateRegular(false);
    }
}
