package com.website.sigma.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FirebaseInitializer
{

    @PostConstruct
    public void initialize()
    {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/sigma-website-31001-firebase-adminsdk-5xfrs-e007d74f1a.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://sigma-website-31001-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
