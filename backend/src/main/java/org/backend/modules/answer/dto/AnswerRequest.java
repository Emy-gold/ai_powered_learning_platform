package org.backend.modules.answer.dto;

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
public class AnswerRequest {

    @NotBlank
    private String answerText;

    private boolean isCorrect;

    @NotNull
    private Long questionId;
}
