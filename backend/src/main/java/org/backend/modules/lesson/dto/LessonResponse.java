package org.backend.modules.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.modules.assignment.dto.AssignmentResponse;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponse {

    private Long id;

    private String title;

    private String description;

    private Integer lessonOrder;

    private Integer duration;

    private boolean preview;

    private List<VideoResponse> videos;

    private List<QuizResponse> quizzes;
}