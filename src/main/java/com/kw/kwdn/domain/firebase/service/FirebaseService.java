package com.kw.kwdn.domain.firebase.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseService {
    private static final String NOTICE = "NOTICE";
    private static final String ALARM = "ALARM";

    public void sendNotification(String title, String body) {
        Message message = makeMessage(title, body, NOTICE);
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("firebase send notification successfully " + response);
        } catch (FirebaseMessagingException e) {
            log.warn("firebase send notification error");
            e.printStackTrace();
        }
    }

    public void sendAlarm(String title, String body) {
        Message message = makeMessage(title, body, ALARM);
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("firebase send notification successfully " + response);
        } catch (FirebaseMessagingException e) {
            log.warn("firebase send notification error");
            e.printStackTrace();
        }
    }

    /**
     * @author Tianea
     * @param title message main content
     * @param body message sub content
     * @param type message type
     * @return A message object with a predetermined title, content, and type is returned.
     */
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
