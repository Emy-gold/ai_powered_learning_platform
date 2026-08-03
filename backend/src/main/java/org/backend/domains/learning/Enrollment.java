package org.backend.domains.learning;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.commun.BaseEntity;
import org.backend.domains.user.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Enrollment extends BaseEntity {

    private LocalDateTime enrollmentDate;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<EnrollmentStatus> status;
    private int completionPercentage;

    @ManyToOne
    @JoinColumn(name = "user",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course",nullable = false)
    private Course course;
}
