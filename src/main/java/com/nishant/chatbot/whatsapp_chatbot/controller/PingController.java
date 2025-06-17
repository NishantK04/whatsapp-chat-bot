package com.nishant.chatbot.whatsapp_chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        System.out.println("✅ Ping route hit!");
        return "pong";
    }
}
