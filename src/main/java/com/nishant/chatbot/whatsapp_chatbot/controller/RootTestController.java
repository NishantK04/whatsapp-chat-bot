package com.nishant.chatbot.whatsapp_chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootTestController {
    @GetMapping("/")
    public String index() {
        return "✅ App is working!";
    }
}
