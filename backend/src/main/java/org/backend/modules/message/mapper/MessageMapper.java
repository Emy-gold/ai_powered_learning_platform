package org.backend.modules.message.mapper;

import org.backend.domains.communication.Message;
import org.backend.domains.user.User;
import org.backend.modules.message.dto.MessageRequest;
import org.backend.modules.message.dto.MessageResponse;
import org.backend.modules.user.dto.UserSummaryResponse;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageResponse toResponse(Message message){
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .sentAt(message.getCreatedAt()) // guessing this maps to BaseEntity.createdAt — confirm
                .sender(toSummaryResponse(message.getSender())) // guessing field name "sender"
                .build();
    }

    private UserSummaryResponse toSummaryResponse(User user){
        return new UserSummaryResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName()
        );
    }

    public Message toEntity(MessageRequest request){
        Message message = new Message();
        message.setContent(request.getContent());
        // chat and sender resolved in the service, same pattern as elsewhere
        return message;
    }
}