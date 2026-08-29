package org.backend.modules.lesson.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Lesson;
import org.backend.modules.lesson.dto.LessonRequest;
import org.backend.modules.lesson.dto.LessonResponse;
import org.backend.modules.quiz.mapper.QuizMapper;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class LessonMapper {

    private final QuizMapper quizMapper;

    public LessonResponse toResponse(Lesson lesson) {


        return LessonResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .lessonOrder(lesson.getLessonOrder())
                .duration(lesson.getDuration())
                .preview(lesson.isPreview())
                .quizzes(lesson.getQuizzes()
                        .stream()
                        .map(quizMapper::toResponse)
                        .toList())
                .build();
    }

    public Lesson toEntity(LessonRequest request) {

        Lesson lesson = new Lesson();

        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setLessonOrder(request.getLessonOrder());
        lesson.setDuration(request.getDuration());
        lesson.setPreview(request.isPreview());

        return lesson;
    }
}