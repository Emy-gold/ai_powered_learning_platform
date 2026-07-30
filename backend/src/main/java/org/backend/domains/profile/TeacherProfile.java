package org.backend.domains.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;
import org.backend.domains.user.User;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TeacherProfile extends BaseEntity {

    private String speciality;
    private int experience;
    private String education;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private User user;
}
