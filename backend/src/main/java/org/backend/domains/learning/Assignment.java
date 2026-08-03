package org.backend.domains.learning;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Assignment extends BaseEntity {

    private String title;
    private String description;
    private Double maxScore;
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "lesson",nullable = false)
    private Lesson lesson;

    @OneToMany(mappedBy = "assignment",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions;

}
