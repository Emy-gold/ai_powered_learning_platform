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
import org.springframework.stereotype.Service;

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
}
