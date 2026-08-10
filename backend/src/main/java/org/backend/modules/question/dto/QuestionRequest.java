package org.backend.modules.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.assessment.QuestionType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {

    @NotBlank
    private String questionText;

    @NotNull
    private Integer points;

    @NotNull
    private Integer orderNumber;

    @NotNull
    private QuestionType questionType;

    @NotNull
    private Long quizId;
}