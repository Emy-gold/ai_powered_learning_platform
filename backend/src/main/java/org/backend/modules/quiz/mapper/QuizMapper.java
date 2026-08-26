package org.backend.modules.quiz.mapper;

import org.backend.domains.assessment.Quiz;
import org.backend.domains.learning.Lesson;
import org.backend.modules.lesson.dto.LessonRequest;
import org.backend.modules.quiz.dto.QuizRequest;
import org.backend.modules.quiz.dto.QuizResponse;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    private final QuestionMapper questionMapper;
    public QuizResponse toResponse(Quiz quiz){

        return QuizResponse.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .timeLimit(quiz.getTimeLimit())
                .questions(quiz.getQuestions()
                        .stream()
                        .map(questionMapper::toResponse)
                        .toList()
                ).build();
    }

    public Quiz toEntity(QuizRequest request) {

        Quiz quiz = new Quiz();

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setTimeLimit(request.getTimeLimit());
        quiz.setPassingScore(request.getPassingScore());

        return quiz;
    }
}
