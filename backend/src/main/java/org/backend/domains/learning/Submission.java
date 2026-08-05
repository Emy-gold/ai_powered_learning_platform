package org.backend.domains.learning;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;
import org.backend.domains.profile.StudentProfile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Submission extends BaseEntity {

    private String content;
    @Enumerated(EnumType.STRING)
    private submissionStatus status = submissionStatus.PENDING;
    private String feedback;
    private Double score;
    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
    private StudentProfile student;

    @ManyToOne
    @JoinColumn(name = "assignment_id",nullable = false)
    private Assignment assignment;

}
