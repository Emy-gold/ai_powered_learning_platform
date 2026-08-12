package org.backend.modules.student.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.student.dto.StudentRequest;
import org.backend.modules.student.dto.StudentResponse;
import org.backend.modules.student.dto.StudentUpdateRequest;
import org.backend.modules.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    
    //------------Post endpoint for creating students--------------
    @PostMapping("{userId}/profile")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<StudentResponse> create(
            @PathVariable Long userId,
            @RequestBody StudentRequest request
            ){
        return ResponseEntity.ok(service.create(userId, request));
    }

    //-----------Get the student by id---------------------------------
    @GetMapping("/{id}/profile")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //----------Get student by user id---------------------------------
    @GetMapping("user/{userId}/profile")
    public ResponseEntity<StudentResponse> getByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    //----------Get all students---------------------------------------
    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //----------Update the student profile ----------------------------
    @PutMapping("{id}/profile")
    @PreAuthorize("hasAnyAuthority('STUDENT','ADMIN')")
    public ResponseEntity<StudentResponse> update(
        @PathVariable Long id,
        StudentUpdateRequest request
    ){
        return ResponseEntity.ok(service.update(id, request));
    }

    //---------Delete the student profile---------------------------------
    @DeleteMapping("{id}/profile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}


