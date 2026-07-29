package org.backend.domains.Enrollement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.commun.BaseEntity;

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
    private List<Status> status;
    private int completionPercentage;

}
