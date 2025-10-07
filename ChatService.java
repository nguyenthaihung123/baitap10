package com.example.chat.service;

import com.example.chat.model.ChatMessage;
import com.example.chat.model.MessageEntity;
import com.example.chat.repository.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ChatService {

    private final MessageRepository repo;

    public ChatService(MessageRepository repo) {
        this.repo = repo;
    }

    public MessageEntity save(ChatMessage dto) {
        MessageEntity e = new MessageEntity();
        e.setMessageId(dto.getId());
        e.setConversationId(dto.getConversationId());
        e.setSender(dto.getSender());
        e.setRecipient(dto.getRecipient());
        e.setContent(dto.getContent());
        e.setTimestamp(Instant.now());
        e.setType(dto.getType());
        e.setStatus("SENT");
        return repo.save(e);
    }

    public Page<MessageEntity> getHistory(String convId, int page, int size) {
        return repo.findByConversationIdOrderByTimestampAsc(convId, PageRequest.of(page, size));
    }
}
