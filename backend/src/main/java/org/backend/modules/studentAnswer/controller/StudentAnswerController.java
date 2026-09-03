package org.backend.modules.studentAnswer.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.studentAnswer.dto.StudentAnswerRequest;
import org.backend.modules.studentAnswer.dto.StudentAnswerResponse;
import org.backend.modules.studentAnswer.service.StudentAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/student-answers")
public class StudentAnswerController {

    private final StudentAnswerService studentAnswerService;

    //--------------------------------Create student answer------------------------------------------------
    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<StudentAnswerResponse> create(@RequestBody StudentAnswerRequest request) {
        return ResponseEntity.ok(studentAnswerService.create(request));
    }

    //--------------------------------Get student answer by id----------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<StudentAnswerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentAnswerService.getById(id));
    }

    //--------------------------------Get all student answers-----------------------------------------------
    @GetMapping
    public ResponseEntity<List<StudentAnswerResponse>> getAll() {
        return ResponseEntity.ok(studentAnswerService.getAll());
    }

    //--------------------------------Update student answer-------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<StudentAnswerResponse> update(@PathVariable Long id, @RequestBody StudentAnswerRequest request) {
        return ResponseEntity.ok(studentAnswerService.update(id, request));
    }

    //--------------------------------Delete student answer-------------------------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentAnswerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}