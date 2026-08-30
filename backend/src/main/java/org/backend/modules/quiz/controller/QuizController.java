package org.backend.modules.quiz.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.lesson.dto.LessonResponse;
import org.backend.modules.quiz.dto.QuizRequest;
import org.backend.modules.quiz.dto.QuizResponse;
import org.backend.modules.quiz.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/quizzes")
public class QuizController {

    private final QuizService quizService;

    //--------------------------------Post endpoint for creating quizzes------------------------------------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<QuizResponse> create(@RequestBody QuizRequest request){
        return ResponseEntity.ok(quizService.create(request));
    }

    //--------------------------------Get the quiz by id--------------------------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<QuizResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(quizService.getById(id));
    }

    //--------------------------------Get all quizzes------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAll(){
        return ResponseEntity.ok(quizService.getAll());
    }

    //-------------------------------Update the quiz--------------------------------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<QuizResponse> update(@PathVariable Long id,@RequestBody QuizRequest request,
        Authentication authentification
    )throws AccessDeniedException{
        Long userId = Long.valueOf(authentification.getName());

        QuizResponse quizResponse = quizService.update(
                id,
                request,
                userId,
                authentification
        );

       return ResponseEntity.ok(quizResponse);
    }

    //---------------------------Delete the lesson----------------------------------------------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('TEACHER','ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication)
        throws AccessDeniedException{
        Long userId = Long.valueOf(authentication.getName());
        quizService.delete(id, userId, authentication);
        return ResponseEntity.noContent().build();
    }
}