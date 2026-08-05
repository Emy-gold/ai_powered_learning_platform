package org.backend.modules.video.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.learning.VideoQuality;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private int duration;

    private int size;

    @NotBlank
    private String url;

    private VideoQuality quality;
}
