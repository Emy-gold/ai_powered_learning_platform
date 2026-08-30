package org.backend.modules.question.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.question.dto.QuestionRequest;
import org.backend.modules.question.dto.QuestionResponse;
import org.backend.modules.question.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/questions")
public class QuestionController {

    private final QuestionService service;

    //-------------------------------Post endpoint for creating questions-----------------------------------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<QuestionResponse> create(@RequestBody QuestionRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    //------------------------------Get the question by id--------------------------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<QuestionResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //-----------------------------Get all the questions----------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //-----------------------------Update the lesson--------------------------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<QuestionResponse> update(
            @PathVariable Long id,
            @RequestBody QuestionRequest request,
            Authentication authentication
    )throws AccessDeniedException{
        Long userId = Long.valueOf(authentication.getName());

        QuestionResponse response = service.update(
                id,
                request,
                userId,
                authentication
        );

        return ResponseEntity.ok(response);
    }

    //---------------------------------------Delete the question--------------------------------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id,Authentication authentication)
        throws AccessDeniedException{

        Long userId = Long.valueOf(authentication.getName());
        service.delete(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }
}