package org.backend.domains.course;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@SuperBuilder
public class Course extends BaseEntity {




}
