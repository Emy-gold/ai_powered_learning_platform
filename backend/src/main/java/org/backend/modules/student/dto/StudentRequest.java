package org.backend.modules.student.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.profile.EducationLevel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotBlank
    private String institution;

    @NotBlank
    private String fieldOfStudy;

    @NotBlank
    private String learningGoal;

    @NotNull
    private EducationLevel educationLevel;
}