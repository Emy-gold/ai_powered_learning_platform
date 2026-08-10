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
public class Question extends BaseEntity {

    private String questionText;
    private Integer points;
    private Integer orderNumber;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "quiz", nullable = false, updatable = false)
    private Quiz quiz;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAnswer> studentAnswers;
}
