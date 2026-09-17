package org.backend.modules.message.controller;

import lombok.RequiredArgsConstructor;
import org.backend.domains.user.User;
import org.backend.modules.message.dto.MessageRequest;
import org.backend.modules.message.dto.MessageResponse;
import org.backend.modules.message.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/messages")
public class MessageController {

    private final MessageService service;

    //------------------------------Post endpoint for sending a message------------------------------
    @PostMapping
    public ResponseEntity<MessageResponse> send(
            @RequestBody MessageRequest request,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.send(request, userId));
    }

    //-----------------------------Get all messages in a chat----------------------------------------
    @GetMapping("chat/{chatId}")
    public ResponseEntity<List<MessageResponse>> getByChat(
            @PathVariable Long chatId,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.getByChat(chatId, userId));
    }

    //---------------------------Delete a message (sender only)---------------------------------------
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        service.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}