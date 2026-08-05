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

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Progress extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    private Integer progressPercentage;
    private boolean completed = false;
    private LocalDateTime lastAccessedAt;
}
