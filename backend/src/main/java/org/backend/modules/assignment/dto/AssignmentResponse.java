package org.backend.modules.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.modules.lesson.dto.LessonResponse;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {

    private Long id;
    private String title;
    private String description;
    private Double maxScore;
    private LocalDateTime dueDate;
    private LessonResponse lesson;
}
