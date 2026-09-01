package org.backend.modules.studentAnswer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudentAnswerResponse {

    private Long id;

    private Long questionId;

    private Long studentId;

    private Long answerId;

    private boolean correct;

    private LocalDateTime submittedAt;
}