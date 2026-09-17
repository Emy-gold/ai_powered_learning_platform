package org.backend.modules.message.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.communication.Chat;
import org.backend.domains.communication.Message;
import org.backend.domains.user.User;
import org.backend.modules.chat.repository.ChatRepository;
import org.backend.modules.message.dto.MessageRequest;
import org.backend.modules.message.dto.MessageResponse;
import org.backend.modules.message.mapper.MessageMapper;
import org.backend.modules.message.repository.MessageRepository;
import org.backend.modules.user.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    //-------------------------------Send a message---------------------------------
    @Transactional
    public MessageResponse send(MessageRequest request, Long senderId){

        Chat chat = chatRepository.findById(request.getChatId())
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        boolean isParticipant = chat.getParticipants().stream()
                .anyMatch(p -> p.getUser().getId().equals(senderId));

        if (!isParticipant) {
            throw new AccessDeniedException("You are not a participant in this chat");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = messageMapper.toEntity(request);
        message.setChat(chat);
        message.setSender(sender);

        return messageMapper.toResponse(messageRepository.save(message));
    }

    //-------------------------------Get all messages in a chat, scoped to participant--
    public List<MessageResponse> getByChat(Long chatId, Long userId){

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        boolean isParticipant = chat.getParticipants().stream()
                .anyMatch(p -> p.getUser().getId().equals(userId));

        if (!isParticipant) {
            throw new AccessDeniedException("You are not a participant in this chat");
        }

        List<MessageResponse> messages = new ArrayList<>();
        for (Message message : messageRepository.findByChatIdOrderByCreatedAtAsc(chatId)) {
            messages.add(messageMapper.toResponse(message));
        }
        return messages;
    }

    //-------------------------------Delete a message (sender only)---------------------
    @Transactional
    public void delete(Long messageId, Long userId){

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getSender().getId().equals(userId)) {
            throw new AccessDeniedException("You can only delete your own messages");
        }

        messageRepository.delete(message);
    }
}