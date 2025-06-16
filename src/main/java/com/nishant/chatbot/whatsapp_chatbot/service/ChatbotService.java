package com.nishant.chatbot.whatsapp_chatbot.service;

import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    public String generateReply(String message) {
        message = message.toLowerCase().trim();  // Normalize for easier matching

        switch (message) {
            case "hi":
            case "Hi":
            case "hey":
            case "Hey":
                return "Hello! 👋 How can I assist you today?";
            case "hello":
                return "Hey there! 👋 How can I help you today?";
            case "help":
                return "You can ask me about our services, timings, or anything else.";
            case "congrats":
                return "🎉 Congratulations! You're all set!";
            case "who made you":
            case "who created you":
            case "who built this bot":
            case "who made this bot":
                return "I was made by Nishant Kumar — an Android & Java developer 👨‍💻.";
            default:
                return "I'm not sure I understand. Try saying 'help'.";
        }
    }
}
