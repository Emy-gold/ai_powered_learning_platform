package org.backend.modules.video.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Lesson;
import org.backend.domains.learning.Video;
import org.backend.modules.lesson.repository.LessonRepository;
import org.backend.modules.video.dto.VideoRequest;
import org.backend.modules.video.dto.VideoResponse;
import org.backend.modules.video.mapper.VideoMapper;
import org.backend.modules.video.repository.VideoRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;
    private final VideoMapper videoMapper;

    //-------------------------------Upload/create a video---------------------------------
    @Transactional
    public VideoResponse create(VideoRequest request, Long userId, org.springframework.security.core.Authentication authentication){

        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin && !userId.equals(lesson.getCreatedBy())) {
            throw new AccessDeniedException("You do not own this lesson");
        }

        Video video = videoMapper.toEntity(request);
        video.setLesson(lesson);

        return videoMapper.toResponse(videoRepository.save(video));
    }

    //-------------------------------Get video by id-----------------------------------------
    public VideoResponse getById(Long id){
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        return videoMapper.toResponse(video);
    }

    //-------------------------------Get all videos for a lesson-----------------------------
    public List<VideoResponse> getByLesson(Long lessonId){
        List<VideoResponse> videos = new ArrayList<>();
        for (Video video : videoRepository.findByLessonId(lessonId)) {
            videos.add(videoMapper.toResponse(video));
        }
        return videos;
    }

    //-------------------------------Delete a video-------------------------------------------
    @Transactional
    public void delete(Long id, Long userId, org.springframework.security.core.Authentication authentication){
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin && !userId.equals(video.getLesson().getCreatedBy())) {
            throw new AccessDeniedException("You do not own this video");
        }

        videoRepository.delete(video);
    }
}