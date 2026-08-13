package org.backend.modules.teacher.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.teacher.dto.TeacherRequest;
import org.backend.modules.teacher.dto.TeacherResponse;
import org.backend.modules.teacher.dto.TeacherUpdateRequest;
import org.backend.modules.teacher.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService service;

    //-----------------Post endpoint for creating teachers---------------------
    @PostMapping("{userId/profile}")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<TeacherResponse> create(
            @PathVariable Long userId,
            @RequestBody TeacherRequest request
            ){
        return ResponseEntity.ok(service.create(userId, request));
    }

    //-----------------Get the teacher by id-------------------------------------
    @GetMapping("/{id}/profile")
    public ResponseEntity<TeacherResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //----------------Get the teacher by user id --------------------------------
    @GetMapping("user/{userId}/profile")
    public ResponseEntity<TeacherResponse> getByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    //---------------Get all the teachers----------------------------------------
    @RequestMapping("api/v1/teachers")
    public ResponseEntity<List<TeacherResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //--------------Update the teacher profile----------------------------------
    @DeleteMapping("{id}/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public ResponseEntity<TeacherResponse> update(
            @PathVariable Long id,
            @RequestBody TeacherUpdateRequest request
    ){
        return ResponseEntity.ok(service.update(id, request));
    }

    //-------------Delete the student profile----------------------------------
    @DeleteMapping("/{id}/profile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
