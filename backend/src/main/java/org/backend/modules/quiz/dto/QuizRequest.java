package org.backend.modules.quiz.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer passingScore;

    @Min(1)
    @NotNull
    private Integer timeLimit;

    @NotNull
    private Long lessonId;
}