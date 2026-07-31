package org.backend.domains.learning;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "progress",cascade = CascadeType.ALL)
    private List<StudentProfile> studentProfiles;

    @OneToMany(mappedBy = "progress",cascade = CascadeType.ALL)
    private List<Course> course;

    @OneToMany(mappedBy = "progress",cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    private int progressPercentage;
    private boolean completed;
    private LocalDateTime lastAccessedAt;
}
