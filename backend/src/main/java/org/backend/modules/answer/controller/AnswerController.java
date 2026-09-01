package org.backend.modules.answer.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.answer.dto.AnswerRequest;
import org.backend.modules.answer.dto.AnswerResponse;
import org.backend.modules.answer.service.AnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/answers")
public class AnswerController {

    private final AnswerService service;

    //--------------------------Post endpoint for creating the answers--------------------------------------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<AnswerResponse> create(@RequestBody AnswerRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    //--------------------------Get endpoints to get the answer by id---------------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<AnswerResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //--------------------------Get all the answers's endpoint----------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<AnswerResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //-------------------------Update the answer endpoint---------------------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseEntity<AnswerResponse> update(@PathVariable Long id, @RequestBody AnswerRequest request, Authentication authentication)
        throws AccessDeniedException {
        Long userId = Long.valueOf(authentication.getName());

        AnswerResponse response = service.update(
                id,
                request,
                userId,
                authentication
        );

        return ResponseEntity.ok(response);
    }

    //------------------------------------------Delete the lesson-------------------------------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication)
        throws AccessDeniedException{

        Long userId = Long.valueOf(authentication.getName());
        service.delete(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }
}
