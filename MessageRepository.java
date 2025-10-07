package com.example.chat.repository;

import com.example.chat.model.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByConversationIdOrderByTimestampAsc(String conversationId);
    Page<MessageEntity> findByConversationIdOrderByTimestampAsc(String conversationId, Pageable pageable);
}
