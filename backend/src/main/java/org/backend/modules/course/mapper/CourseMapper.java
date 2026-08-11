package org.backend.modules.course.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Course;
import org.backend.modules.category.dto.CategoryResponse;
import org.backend.modules.course.dto.CourseRequest;
import org.backend.modules.course.dto.CourseResponse;
import org.backend.modules.lesson.dto.LessonResponse;
import org.backend.modules.teacher.dto.TeacherResponse;
import org.backend.modules.teacher.mapper.TeacherMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    private final TeacherMapper teacherMapper;

    public CourseResponse toResponse(Course course) {

        List<LessonResponse> lessons = new ArrayList<>();

        if (course.getLessons() != null) {
            for (var lesson : course.getLessons()) {

                LessonResponse lessonResponse = LessonResponse.builder()
                        .id(lesson.getId())
                        .title(lesson.getTitle())
                        .description(lesson.getDescription())
                        .lessonOrder(lesson.getLessonOrder())
                        .duration(lesson.getDuration())
                        .preview(lesson.isPreview())
                        .build();

                lessons.add(lessonResponse);
            }
        }

        TeacherResponse teacher = null;

        if (course.getTeacher() != null) {
            teacher = teacherMapper.toResponse(course.getTeacher());
        }

        CategoryResponse category = null;

        if (course.getCategory() != null) {
            category = CategoryResponse.builder()
                    .id(course.getCategory().getId())
                    .name(course.getCategory().getName())
                    .description(course.getCategory().getDescription())
                    .icon(course.getCategory().getIcon())
                    .build();
        }

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .level(course.getLevel())
                .language(course.getLanguage())
                .status(course.getStatus())
                .lessons(lessons)
                .teacher(teacher)
                .category(category)
                .build();
    }

    public Course  toEntity(CourseRequest request){

            Course course = new Course();
            course.setTitle(request.getTitle());
            course.setDescription(request.getDescription());
            course.setLanguage(request.getLanguage());
            course.setLevel(request.getLevel());

            return course;
    }
}