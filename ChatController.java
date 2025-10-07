package com.example.chat.controller;

import com.example.chat.model.ChatMessage;
import com.example.chat.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat.private")
    public void privateMessage(@Payload ChatMessage message, Principal principal) {
        if (principal != null) {
            message.setSender(principal.getName());
        }
        String a = message.getSender();
        String b = message.getRecipient();
        if (a == null || b == null) {
            return;
        }
        String conv = a.compareTo(b) < 0 ? a + "_" + b : b + "_" + a;
        message.setConversationId(conv);
        chatService.save(message);
        messagingTemplate.convertAndSendToUser(message.getRecipient(), "/queue/messages", message);
        messagingTemplate.convertAndSendToUser(message.getSender(), "/queue/ack", message.getId());
    }
}
