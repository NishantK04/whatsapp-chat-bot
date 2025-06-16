package com.nishant.chatbot.whatsapp_chatbot.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nishant.chatbot.whatsapp_chatbot.service.ChatbotService;
import com.nishant.chatbot.whatsapp_chatbot.service.FirebaseService;

@RestController
@RequestMapping("/webhook")
public class WhatsAppController {

    @Autowired
    private ChatbotService chatbotService;

    @Autowired
    private FirebaseService firebaseService;  
    @GetMapping
    public ResponseEntity<String> verifyWebhook(@RequestParam("hub.mode") String mode,
                                                @RequestParam("hub.verify_token") String token,
                                                @RequestParam("hub.challenge") String challenge) {
        System.out.println("🌐 Received webhook verification request");
                                            
        if ("subscribe".equals(mode) && "123456".equals(token)) {
            System.out.println("✅ Webhook verified by Meta.");
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody Map<String, Object> body) {
        System.out.println("✅ Webhook POST /webhook hit!");
        System.out.println("📩 Incoming Payload: " + body);

        try {
            Map entry = ((List<Map>) body.get("entry")).get(0);
            Map changes = ((List<Map>) entry.get("changes")).get(0);
            Map value = (Map) changes.get("value");

            if (value.containsKey("messages")) {
                Map message = ((List<Map>) value.get("messages")).get(0);
                String from = (String) message.get("from");
                String text = (String) ((Map) message.get("text")).get("body");

                System.out.println("👤 User (" + from + ") sent: " + text);

                String reply = chatbotService.generateReply(text);
                System.out.println("🤖 Reply: " + reply);

                // 🔥 Log the chat in Firestore
                firebaseService.logChat(from, text, reply);

                // ✅ Send back the reply
                sendWhatsAppMessage(from, reply);
            } else {
                System.out.println("⚠️ No message object found (likely a delivery status update).");
            }

        } catch (Exception e) {
            System.out.println("❌ Exception in receiveMessage:");
            e.printStackTrace();
        }

        return ResponseEntity.ok("EVENT_RECEIVED");
    }

    private void sendWhatsAppMessage(String to, String message) {
        try {
            String phoneNumberId = "602040573002445";  // Replace with your ID
            String token = "EAAROftk2jR4BO2afxU73VWHExuNt9GTYo85CneysnKZA5BM9GPh2PgUxqU5cxGjDcO6QKbyrYGuEay6qDuoeN16WZAZCdxctb9LWAI7ZC3CYn3DTc8JRX4kxbYZA2f50k0g0BXRXkNs0BEA6lOSZCa7n8sEvmkwHj2nV6tGZAsufcNmwdFPElNMm4oyDk5NecbIIK4hLK9bwndGjnCh2kLKFQETURZCUzMXoeziXScGMLvmTNqcZD";

            URL url = new URL("https://graph.facebook.com/v18.0/" + phoneNumberId + "/messages");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = "{\n" +
                    "  \"messaging_product\": \"whatsapp\",\n" +
                    "  \"to\": \"" + to + "\",\n" +
                    "  \"type\": \"text\",\n" +
                    "  \"text\": {\n" +
                    "    \"body\": \"" + message + "\"\n" +
                    "  }\n" +
                    "}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println("📤 WhatsApp API Response Code: " + code);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("📨 WhatsApp API Response Body: " + response.toString());
            }

        } catch (Exception e) {
            System.out.println("❌ Error sending WhatsApp message:");
            e.printStackTrace();
        }
    }
}
