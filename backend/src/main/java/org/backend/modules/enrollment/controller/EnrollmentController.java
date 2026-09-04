package org.backend.modules.enrollment.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.enrollment.dto.EnrollmentRequest;
import org.backend.modules.enrollment.dto.EnrollmentResponse;
import org.backend.modules.enrollment.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService service;

    //------------------------------Post endpoint for enrolling in a course------------------------
    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<EnrollmentResponse> enroll(
            @RequestBody EnrollmentRequest request,
            Authentication authentication
    ){
        Long userId = Long.valueOf(authentication.getName());

        EnrollmentResponse response = service.enroll(request, userId);

        return ResponseEntity.ok(response);
    }

    //------------------------------Get enrollment by id--------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<EnrollmentResponse> getById(@PathVariable Long id, Authentication authentication){
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(service.getById(id, userId));
    }

    //------------------------------Get all enrollments for the current student---------------------
    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<List<EnrollmentResponse>> getMyEnrollments(Authentication authentication){
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(service.getByStudent(userId));
    }

    //------------------------------Unenroll / cancel an enrollment----------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Void> unenroll(
            @PathVariable Long id,
            Authentication authentication
    ){
        Long userId = Long.valueOf(authentication.getName());
        service.unenroll(id, userId);
        return ResponseEntity.noContent().build();
    }
}