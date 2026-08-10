package org.backend.modules.studentAnswer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnswerRequest {

    @NotNull
    private Long questionId;

    @NotNull
    private Long answerId;
}