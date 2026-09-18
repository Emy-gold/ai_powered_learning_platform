package org.backend.modules.video.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Video;
import org.backend.modules.lesson.mapper.LessonMapper;
import org.backend.modules.video.dto.VideoRequest;
import org.backend.modules.video.dto.VideoResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final LessonMapper lessonMapper;

    public VideoResponse toResponse(Video video){
        return VideoResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .duration(video.getDuration())
                .size(video.getSize())
                .url(video.getUrl())
                .quality(video.getQuality())
                .lesson(lessonMapper.toResponse(video.getLesson()))
                .build();
    }

    public Video toEntity(VideoRequest request){
        Video video = new Video();
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setDuration(request.getDuration());
        video.setSize(request.getSize());
        video.setUrl(request.getUrl());
        video.setQuality(request.getQuality());
        // lesson resolved in the service, same pattern as elsewhere
        return video;
    }
}