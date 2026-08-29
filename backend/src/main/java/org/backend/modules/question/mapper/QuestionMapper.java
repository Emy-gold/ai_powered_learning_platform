package org.backend.modules.question.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.assessment.Question;
import org.backend.modules.question.dto.QuestionRequest;
import org.backend.modules.question.dto.QuestionResponse;
import org.backend.modules.quiz.dto.QuizResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final AnswerMapper answerMapper;

    public QuestionResponse toResponse(Question question){

        return QuestionResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .points(question.getPoints())
                .orderNumber(question.getOrderNumber())
                .questionType(question.getQuestionType())
                .answers(question.getAnswers()
                        .stream()
                        .map(answerMapper::toResponse)
                        .toList()
                ).build();
    }

    public Question toEntity(QuestionRequest request){

        Question question = new Question();

        question.setQuestionText(request.getQuestionText());
        question.setPoints(request.getPoints());
        question.setQuestionType(request.getQuestionType());
        question.setOrderNumber(request.getOrderNumber());

        return question;
    }
}
