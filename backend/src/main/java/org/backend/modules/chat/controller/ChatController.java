package org.backend.modules.chat.controller;

import lombok.RequiredArgsConstructor;
import org.backend.domains.user.User;
import org.backend.modules.chat.dto.ChatRequest;
import org.backend.modules.chat.dto.ChatResponse;
import org.backend.modules.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/chats")
public class ChatController {

    private final ChatService service;

    //------------------------------Post endpoint for creating a chat--------------------------------
    @PostMapping
    public ResponseEntity<ChatResponse> create(
            @RequestBody ChatRequest request,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.create(request, userId));
    }

    //------------------------------Get chat by id, scoped to participant----------------------------
    @GetMapping("{id}")
    public ResponseEntity<ChatResponse> getById(
            @PathVariable Long id,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.getById(id, userId));
    }

    //-----------------------------Get all chats for the current user---------------------------------
    @GetMapping
    public ResponseEntity<List<ChatResponse>> getMyChats(Authentication authentication){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.getMyChats(userId));
    }

    //---------------------------Delete/leave the chat--------------------------------------------------
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