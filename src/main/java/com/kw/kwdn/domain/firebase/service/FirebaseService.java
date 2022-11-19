package com.kw.kwdn.domain.firebase.service;

import com.google.firebase.messaging.*;
import com.kw.kwdn.domain.firebase.service.enums.TopicType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseService {

    public void sendAlarm(String title, String body, TopicType type) {
        Message message = makeMessage(title, body, type.value());
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("firebase send notification successfully " + response);
        } catch (FirebaseMessagingException e) {
            log.warn("firebase send notification error");
            e.printStackTrace();
        }
    }

    public void subscribeToTopic(TopicType type, String userId) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(List.of(userId), type.value());
        } catch (FirebaseMessagingException e) {
            throw new IllegalStateException("firebase token을 topic 등록하는 도중에 문제가 발생하였습니다.");
        }
    }

    public void unsubscribeToTopic(TopicType type, String token) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(token), type.value());
        } catch (FirebaseMessagingException e) {
            throw new IllegalStateException("firebase token을 topic 등록 해제하는 도중에 문제가 발생하였습니다.");
        }
    }

    private Message makeMessage(String title, String body, String type) {
        return Message.builder()
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setPriority(AndroidConfig.Priority.HIGH)
                                .build())
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
                .setTopic(type)
                .build();
    }
}
