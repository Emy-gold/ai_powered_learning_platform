package org.backend.domains.learning;

import jakarta.persistence.*;
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
public class Video extends BaseEntity {

    private String title;
    private String description;
    private int duration;
    private int size;
    private String url;

    @Enumerated(EnumType.STRING)
    private VideoQuality quality;

    @ManyToOne
    @JoinColumn(name = "lesson", nullable = false, updatable = true)
    private Lesson lesson;
}
