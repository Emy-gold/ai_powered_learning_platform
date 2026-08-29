package org.backend.modules.answer.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.assessment.Answer;
import org.backend.modules.answer.dto.AnswerRequest;
import org.backend.modules.answer.dto.AnswerResponse;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {

    public AnswerResponse toResponse(Answer answer){
        return AnswerResponse.builder()
                .id(answer.getId())
                .answerText(answer.getAnswerText())
                .build();
    }

    public Answer toEntity(AnswerRequest request){

        Answer answer = new Answer();

        answer.setAnswerText(request.getAnswerText());
        answer.setCorrect(request.isCorrect());

        return answer;
    }
}
