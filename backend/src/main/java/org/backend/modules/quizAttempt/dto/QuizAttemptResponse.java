package org.backend.modules.quizAttempt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.modules.quiz.dto.QuizResponse;
import org.backend.modules.student.dto.StudentResponse;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttemptResponse {

    private Long id;

    private StudentResponse student;

    private QuizResponse quiz;

    private Double score;

    private Boolean passed;

    private LocalDateTime startedAt;

    private LocalDateTime submittedAt;
}