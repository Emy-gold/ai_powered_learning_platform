package org.backend.modules.chat.mapper;

import org.backend.domains.communication.Chat;
import org.backend.domains.communication.ChatParticipant;
import org.backend.domains.user.User;
import org.backend.modules.chat.dto.ChatRequest;
import org.backend.modules.chat.dto.ChatResponse;
import org.backend.modules.user.dto.UserSummaryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatMapper {

    public ChatResponse toResponse(Chat chat){

        List<UserSummaryResponse> participants = new ArrayList<>();
        for (ChatParticipant participant : chat.getParticipants()){
            participants.add(toSummaryResponse(participant.getUser()));
        }

        return ChatResponse.builder()
                .id(chat.getId())
                .title(chat.getTitle())
                .createdAt(chat.getCreatedAt())
                .updatedAt(chat.getModifiedAt())
                .participants(participants)
                .build();
    }

    private UserSummaryResponse toSummaryResponse(User user){
        return new UserSummaryResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName()
        );
    }

    public Chat toEntity(ChatRequest request){
        Chat chat = new Chat();
        chat.setTitle(request.getTitle());
        return chat;
    }
}