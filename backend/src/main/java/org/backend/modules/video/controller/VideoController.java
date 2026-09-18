package org.backend.modules.video.controller;

import lombok.RequiredArgsConstructor;
import org.backend.domains.user.User;
import org.backend.modules.video.dto.VideoRequest;
import org.backend.modules.video.dto.VideoResponse;
import org.backend.modules.video.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/videos")
public class VideoController {

    private final VideoService service;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<VideoResponse> create(
            @RequestBody VideoRequest request,
            Authentication authentication
    ) throws AccessDeniedException {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.create(request, userId, authentication));
    }

    @GetMapping("{id}")
    public ResponseEntity<VideoResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("lesson/{lessonId}")
    public ResponseEntity<List<VideoResponse>> getByLesson(@PathVariable Long lessonId){
        return ResponseEntity.ok(service.getByLesson(lessonId));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication
    ) throws AccessDeniedException {
        Long userId = ((User) authentication.getPrincipal()).getId();
        service.delete(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }
}