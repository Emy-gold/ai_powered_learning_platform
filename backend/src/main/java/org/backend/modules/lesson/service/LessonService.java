package org.backend.modules.lesson.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Course;
import org.backend.domains.learning.Lesson;
import org.backend.modules.course.repository.CourseRepository;
import org.backend.modules.lesson.dto.LessonRequest;
import org.backend.modules.lesson.dto.LessonResponse;
import org.backend.modules.lesson.mapper.LessonMapper;
import org.backend.modules.lesson.repository.LessonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final CourseRepository courseRepository;

    //-------------------------------Create the lesson function---------------------------------
    @Transactional
    public LessonResponse create(LessonRequest request) {

        Lesson lesson = lessonMapper.toEntity(request);
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        lesson.setCourse(course);
        return lessonMapper.toResponse(lessonRepository.save(lesson));
    }

    //-------------------------------------Get lesson by id--------------------------------------
    public LessonResponse getById(Long id){
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The lesson not found"));

        return lessonMapper.toResponse(lesson);
    }

    //-------------------------------------Get all the lesson------------------------------------
    public List<LessonResponse> getAll(){
        List<LessonResponse> lessons = new ArrayList<>();
        for(Lesson lesson : lessonRepository.findAll()){
            lessons.add(lessonMapper.toResponse(lesson));
        }
        return lessons;
    }

    //---------------------------------Update the lesson----------------------------------------
    @Transactional
    public LessonResponse update(Long id, LessonRequest request, Long userId, Authentication authentication)
            throws AccessDeniedException {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The lesson does not exists"));

        boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if(!isAdmin && !lesson.getCreatedBy().equals(userId)){
            throw new AccessDeniedException("You do not have access to this lesson");
        };

        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setLessonOrder(request.getLessonOrder());
        lesson.setDuration(request.getDuration());
        lesson.setPreview(request.isPreview());

        Lesson saved = lessonRepository.save(lesson);
        return lessonMapper.toResponse(saved);
    }

    //------------------------Delete the lesson-----------------------------------------------
    @Transactional
    public void delete(Long id, Long userId,Authentication authentication) throws AccessDeniedException {
        Lesson lesson = lessonRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Lesson does not exist"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin && !lesson.getCreatedBy().equals(userId)) {
            throw new AccessDeniedException("You do not own this lesson");
        }

        lessonRepository.deleteById(id);
    }
}
