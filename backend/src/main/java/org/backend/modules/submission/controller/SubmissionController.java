package org.backend.modules.submission.controller;

import lombok.RequiredArgsConstructor;
import org.backend.domains.user.User;
import org.backend.modules.submission.dto.SubmissionRequest;
import org.backend.modules.submission.dto.SubmissionResponse;
import org.backend.modules.submission.service.SubmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/submissions")
public class SubmissionController {

    private final SubmissionService service;

    //------------------------------Post endpoint for submitting an assignment-----------------------
    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<SubmissionResponse> submit(
            @RequestBody SubmissionRequest request,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.submit(request, userId));
    }

    //-----------------------------Get submission by id, scoped to owner-----------------------------
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<SubmissionResponse> getById(
            @PathVariable Long id,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.getById(id, userId));
    }

    //---------------------------Get all submissions for the current student---------------------------
    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<SubmissionResponse>> getMySubmissions(Authentication authentication){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.getMySubmissions(userId));
    }
}