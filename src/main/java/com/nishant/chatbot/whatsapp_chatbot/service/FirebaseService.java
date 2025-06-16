package com.nishant.chatbot.whatsapp_chatbot.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseService {

    public void logChat(String from, String message, String reply) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            Map<String, Object> chatLog = new HashMap<>();
            chatLog.put("from", from);
            chatLog.put("message", message);
            chatLog.put("reply", reply);
            chatLog.put("timestamp", Instant.now().toString());

            db.collection("chats").add(chatLog);

            System.out.println("📝 Chat logged to Firestore");
        } catch (Exception e) {
            System.out.println("❌ Firestore log failed:");
            e.printStackTrace();
        }
    }
}