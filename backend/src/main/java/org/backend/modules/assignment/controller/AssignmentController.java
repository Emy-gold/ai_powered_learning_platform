package org.backend.modules.assignment.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.assignment.dto.AssignmentRequest;
import org.backend.modules.assignment.dto.AssignmentResponse;
import org.backend.modules.assignment.service.AssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/assignments")
public class AssignmentController {

    private final AssignmentService service;

    //------------------------------Post endpoint for creating assignments--------------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<AssignmentResponse> create(@RequestBody AssignmentRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    //------------------------------Get the assignment by id----------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<AssignmentResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //-----------------------------Get all assignments-----------------------------------------------
    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //---------------------------Update the assignment------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<AssignmentResponse> update(
            @PathVariable Long id,
            @RequestBody AssignmentRequest request,
            Authentication authentication
    ){
        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(service.update(id, request, userId, authentication));
    }

    //---------------------------Delete the assignment------------------------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Authentication authentication
    ){
        Long userId = Long.valueOf(authentication.getName());
        service.delete(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }
}