package org.backend.modules.submission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.learning.submissionStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponse {

    private Long id;

    private String content;

    private submissionStatus status;

    private String feedback;

    private Double score;

    private LocalDateTime submittedAt;

    private Long assignmentId;
}
