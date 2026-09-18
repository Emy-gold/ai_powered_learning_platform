package org.backend.modules.progress.controller;

import lombok.RequiredArgsConstructor;
import org.backend.domains.user.User;
import org.backend.modules.progress.dto.ProgressResponse;
import org.backend.modules.progress.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/progress")
public class ProgressController {

    private final ProgressService service;

    //-----------------------------Get progress for the current student in a course------------------
    @GetMapping("course/{courseId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<ProgressResponse>> getByCourse(
            @PathVariable Long courseId,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.getByCourse(userId, courseId));
    }

    //---------------------------Update progress on a lesson-------------------------------------------
    @PutMapping("lesson/{lessonId}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<ProgressResponse> updateProgress(
            @PathVariable Long lessonId,
            @RequestParam Integer progressPercentage,
            Authentication authentication
    ){
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(service.updateProgress(lessonId, progressPercentage, userId));
    }
}