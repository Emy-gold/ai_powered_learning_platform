package org.backend.domains.learning;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;
import org.backend.domains.profile.TeacherProfile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@SuperBuilder
public class Course extends BaseEntity {

    private String title;
    private String description;


    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherProfile teacher;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false, updatable = true)
    private Category category;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Progress> progresses;

}
