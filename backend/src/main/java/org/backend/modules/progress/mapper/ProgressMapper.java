package org.backend.modules.progress.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Progress;
import org.backend.modules.course.mapper.CourseMapper;
import org.backend.modules.lesson.mapper.LessonMapper;
import org.backend.modules.progress.dto.ProgressResponse;
import org.backend.modules.student.mapper.StudentMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProgressMapper {

    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final LessonMapper lessonMapper;

    public ProgressResponse toResponse(Progress progress){
        return ProgressResponse.builder()
                .id(progress.getId())
                .student(studentMapper.toResponse(progress.getStudent()))
                .course(courseMapper.toResponse(progress.getCourse()))
                .lesson(lessonMapper.toResponse(progress.getLesson()))
                .progressPercentage(progress.getProgressPercentage())
                .completed(progress.isCompleted())
                .build();
    }
}