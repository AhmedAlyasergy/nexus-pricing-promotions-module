package com.nexus.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import java.io.FileInputStream;

public class FirebaseService {
    private static volatile FirebaseService instance;
    private Firestore db;

    private FirebaseService() {
        try {
            // Note: serviceAccountKey.json must be in the main NexuesLogistics folder
            FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
            FirebaseApp.initializeApp(options);
            this.db = FirestoreClient.getFirestore();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static FirebaseService getInstance() {
        if (instance == null) {
            synchronized (FirebaseService.class) {
                if (instance == null) instance = new FirebaseService();
            }
        }
        return instance;
    }
    public Firestore getDb() { return db; }
}