package org.backend.domains.assessment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer extends BaseEntity {

    private String answerText;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false,updatable = true)
    private Question question;

    @OneToMany(mappedBy = "answer_id",cascade = CascadeType.ALL)
    private List<StudentAnswer> studentAnswers;
}
