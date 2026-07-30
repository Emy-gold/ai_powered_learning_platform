package org.backend.domains.profile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;
import org.backend.domains.user.User;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentProfile extends BaseEntity {

    private String institution;
    private String fieldOfStudy;
    private String learningGoal;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<EducationLevel> educationLevel;


    @OneToOne
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private User user;
}
