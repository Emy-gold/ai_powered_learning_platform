package org.backend.modules.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.modules.lesson.dto.LessonResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequest {

    private String title;

    private String description;

    private Long lessonId;
}
