package org.backend.modules.question.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.assessment.QuestionType;
import org.backend.modules.answer.dto.AnswerResponse;
import org.backend.modules.quiz.dto.QuizResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class QuestionResponse {

    private Long id;
    private String questionText;
    private Integer points;
    private Integer orderNumber;
    private QuestionType questionType;
    private List<AnswerResponse> answers;
}
