package org.backend.modules.lesson.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.lesson.dto.LessonRequest;
import org.backend.modules.lesson.dto.LessonResponse;
import org.backend.modules.lesson.service.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lessons")
public class LessonController {

    private final LessonService service;

    //------------------------------Post endpoint for creating lessons-----------------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<LessonResponse> create(@RequestBody LessonRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    //------------------------------Get the lesson by id-------------------------------------------
    @GetMapping({"{id}"})
    public ResponseEntity<LessonResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //-----------------------------Get All lessons ------------------------------------------------
    @GetMapping
    public ResponseEntity<List<LessonResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //---------------------------Update the lesson-------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<LessonResponse> update(
            @PathVariable Long id,
            @RequestBody LessonRequest request,
            Authentication authentication
    ) throws AccessDeniedException {
        Long userId = Long.valueOf(authentication.getName());

        LessonResponse response = service.update(
                id,
                request,
                userId,
                authentication
        );

        return ResponseEntity.ok(response);
    }

    //--------------------------Delete the lesson----------------------------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable  Long id,Authentication authentication
    ) throws AccessDeniedException{

        Long userId = Long.valueOf(authentication.getName());
        service.delete(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }

}
