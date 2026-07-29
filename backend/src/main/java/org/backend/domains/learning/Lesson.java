package org.backend.domains.learning;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.commun.BaseEntity;


@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lesson extends BaseEntity {

    private String title;
    private String description;
    private String lessonOrder;
    private int duration;
    private boolean isPreview;


}
