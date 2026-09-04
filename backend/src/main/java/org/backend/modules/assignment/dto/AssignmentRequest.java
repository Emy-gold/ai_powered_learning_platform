package org.backend.modules.assignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AssignmentRequest {

    @NotBlank
    private String title;

    private String description;

    private Double maxScore;

    private LocalDateTime dueDate;

    @NotNull
    private Long lessonId;
}
