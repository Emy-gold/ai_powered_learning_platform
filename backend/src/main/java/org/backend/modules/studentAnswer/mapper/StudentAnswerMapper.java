package org.backend.modules.studentAnswer.mapper;

import org.backend.domains.assessment.StudentAnswer;
import org.backend.modules.studentAnswer.dto.StudentAnswerResponse;
import org.springframework.stereotype.Component;

@Component
public class StudentAnswerMapper {

    public StudentAnswerResponse toResponse(StudentAnswer studentAnswer) {

        return StudentAnswerResponse.builder()
                .id(studentAnswer.getId())
                .studentId(studentAnswer.getStudent().getId())
                .questionId(studentAnswer.getQuestion().getId())
                .answerId(studentAnswer.getAnswer().getId())
                .correct(studentAnswer.isCorrect())
                .submittedAt(studentAnswer.getSubmittedAt())
                .build();
    }
}