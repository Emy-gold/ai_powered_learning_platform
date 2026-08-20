package org.backend.modules.course.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.course.dto.CourseRequest;
import org.backend.modules.course.dto.CourseResponse;
import org.backend.modules.course.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    //------------------------Post endpoint for creating courses---------------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    public ResponseEntity<CourseResponse> create(@RequestBody CourseRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    //-------------------------Get the course by id------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //--------------------------Get all the courses -------------------------------------------
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //-------------------------Get by teacher id----------------------------------------------
    @GetMapping("teacher/teacherId")
    public ResponseEntity<List<CourseResponse>> getByTeacherId(@PathVariable Long teacherId){
        return ResponseEntity.ok(service.getByTeacherId(teacherId));
    }

    //-------------------------Put the course-------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<CourseResponse> update(
            @PathVariable Long id,
            @RequestBody CourseRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    //------------------------Delete the course----------------------------------------------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
