package org.backend.modules.video.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.backend.domains.learning.VideoQuality;
import org.backend.modules.lesson.dto.LessonResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class VideoResponse {

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Integer size;
    private String url;
    private VideoQuality quality;
    private LessonResponse lesson;
}
