package com.kw.kwdn.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Configuration
public class FirebaseConfig {
    @Value("${fcm.secret}")
    private String SECRET_FILE_PATH;
    @Value("${fcm.app-name}")
    private String FIREBASE_APP_NAME;

    @Bean
    public FirebaseApp initialize() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(SECRET_FILE_PATH);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        List<FirebaseApp> appList = FirebaseApp.getApps();
        for(FirebaseApp app : appList)
            if(app.getName().equals(FIREBASE_APP_NAME))
                return app;
        return FirebaseApp.initializeApp(options, FIREBASE_APP_NAME);
    }
}
