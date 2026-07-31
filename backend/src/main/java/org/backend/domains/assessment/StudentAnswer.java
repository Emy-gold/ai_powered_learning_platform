package org.backend.domains.assessment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;
import org.backend.domains.learning.Course;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentAnswer extends BaseEntity {

    private boolean isCorrect;

    @Column(updatable = false)
    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "question", nullable = false)
    private Question question;


    @ManyToOne
    @JoinColumn(name = "answer")
    private Answer answer;

}
