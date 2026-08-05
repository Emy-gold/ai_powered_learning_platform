package org.backend.modules.progress.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.modules.course.dto.CourseResponse;
import org.backend.modules.lesson.dto.LessonResponse;
import org.backend.modules.student.dto.StudentResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressResponse {

    private Long id;
    private StudentResponse student;
    private CourseResponse course;
    private LessonResponse lesson;
    private Integer progressPercentage;
    private boolean completed;
}
