package org.backend.modules.chat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.communication.Chat;
import org.backend.domains.communication.ChatParticipant;
import org.backend.domains.user.User;
import org.backend.modules.chat.dto.ChatRequest;
import org.backend.modules.chat.dto.ChatResponse;
import org.backend.modules.chat.mapper.ChatMapper;
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
    private final UserRepository userRepository;
    private final ChatMapper chatMapper;

    //-------------------------------Create a chat---------------------------------
    @Transactional
    public ChatResponse create(ChatRequest request, Long creatorId){

        Chat chat = chatMapper.toEntity(request);

        List<ChatParticipant> participants = new ArrayList<>();

        // make sure the creator is always included, even if the client forgot to list them
        List<Long> participantIds = new ArrayList<>(request.getParticipantIds());
        if (!participantIds.contains(creatorId)) {
            participantIds.add(creatorId);
        }

        for (Long userId : participantIds) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));

            ChatParticipant participant = ChatParticipant.builder()
                    .chat(chat)
                    .user(user)
                    .build();

            participants.add(participant);
        }

        chat.setParticipants(participants);

        return chatMapper.toResponse(chatRepository.save(chat));
    }

    //-------------------------------Get chat by id, scoped to a participant--------
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

    //-------------------------------Get all chats for the current user-------------
    public List<ChatResponse> getMyChats(Long userId){
        List<ChatResponse> chats = new ArrayList<>();
        for (Chat chat : chatRepository.findByParticipantsUserId(userId)) {
            chats.add(chatMapper.toResponse(chat));
        }
        return chats;
    }

    //-------------------------------Delete/leave a chat-----------------------------
    @Transactional
    public void delete(Long id, Long userId){
        Chat chat = chatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        boolean isParticipant = chat.getParticipants().stream()
                .anyMatch(p -> p.getUser().getId().equals(userId));

        if (!isParticipant) {
            throw new AccessDeniedException("You are not a participant in this chat");
        }

        chatRepository.delete(chat);
    }
}