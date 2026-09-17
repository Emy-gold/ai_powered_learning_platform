package org.backend.modules.chat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.communication.Chat;
import org.backend.domains.communication.ChatParticipant;
import org.backend.domains.user.User;
import org.backend.modules.chat.dto.ChatRequest;
import org.backend.modules.chat.dto.ChatResponse;
import org.backend.modules.chat.mapper.ChatMapper;
import org.backend.modules.chat.repository.ChatParticipantRepository;
import org.backend.modules.chat.repository.ChatRepository;
import org.backend.modules.user.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    @Transactional
    public ChatResponse create(ChatRequest request, Long creatorId){

        Chat chat = chatMapper.toEntity(request);

        List<Long> participantIds = new ArrayList<>(request.getParticipantIds());
        if (!participantIds.contains(creatorId)) {
            participantIds.add(creatorId);
        }

        chat.setGroup(participantIds.size() > 2);

        List<ChatParticipant> participants = new ArrayList<>();
        for (Long userId : participantIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));

            participants.add(ChatParticipant.builder()
                    .chat(chat)
                    .user(user)
                    .build());
        }

        chat.setParticipants(participants);

        return chatMapper.toResponse(chatRepository.save(chat));
    }

    public ChatResponse getById(Long id, Long userId){
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        boolean isParticipant = chat.getParticipants().stream()
                .anyMatch(p -> p.getUser().getId().equals(userId));

        if (!isParticipant) {
            throw new AccessDeniedException("You are not a participant in this chat");
        }

        return chatMapper.toResponse(chat);
    }

    public List<ChatResponse> getMyChats(Long userId){
        List<ChatResponse> chats = new ArrayList<>();
        for (Chat chat : chatRepository.findByParticipantsUserId(userId)) {
            chats.add(chatMapper.toResponse(chat));
        }
        return chats;
    }

    @Transactional
    public void leave(Long chatId, Long userId){

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        boolean isParticipant = chat.getParticipants().stream()
                .anyMatch(p -> p.getUser().getId().equals(userId));

        if (!isParticipant) {
            throw new AccessDeniedException("You are not a participant in this chat");
        }

        if (!chat.isGroup()) {
            chatRepository.delete(chat);
            return;
        }

        ChatParticipant participant = chatParticipantRepository
                .findByChatIdAndUserId(chatId, userId)
                .orElseThrow(() -> new RuntimeException("You are not a participant in this chat"));

        chatParticipantRepository.delete(participant);
    }
}