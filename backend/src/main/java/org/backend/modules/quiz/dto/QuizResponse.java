package org.backend.modules.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.modules.lesson.dto.LessonResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    private Long id;
    private String title;
    private String description;
    private Integer passingScore;
    private Integer timeLimit;
    private LessonResponse lesson;
    private List<QuestionResponse> questions;
}
