package org.backend.modules.submission.mapper;

import org.backend.domains.learning.Submission;
import org.backend.modules.submission.dto.SubmissionRequest;
import org.backend.modules.submission.dto.SubmissionResponse;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {

    public SubmissionResponse toResponse(Submission submission){
        return SubmissionResponse.builder()
                .id(submission.getId())
                .content(submission.getContent())
                .status(submission.getStatus())
                .feedback(submission.getFeedback())
                .score(submission.getScore())
                .submittedAt(submission.getCreatedAt()) // guessing this maps to BaseEntity.createdAt
                .assignmentId(submission.getAssignment().getId()) // guessing field name "assignment"
                .build();
    }

    public Submission toEntity(SubmissionRequest request){
        Submission submission = new Submission();
        submission.setContent(request.getContent());
        // assignment + student resolved in the service, same pattern as elsewhere
        return submission;
    }
}