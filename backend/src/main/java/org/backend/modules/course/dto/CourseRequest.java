package org.backend.modules.course.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.learning.Language;
import org.backend.domains.learning.Level;
import org.backend.domains.learning.Status;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CourseRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Level level;

    @NotNull
    private Language language;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long teacherId;
}
