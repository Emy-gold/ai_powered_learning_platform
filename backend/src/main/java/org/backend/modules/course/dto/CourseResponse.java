package org.backend.modules.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.backend.domains.learning.Language;
import org.backend.domains.learning.Level;
import org.backend.domains.learning.Status;
import org.backend.modules.lesson.dto.LessonResponse;
import org.backend.modules.teacher.dto.TeacherResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private Level level;
    private Language language;
    private Status status;
    private List<LessonResponse> lessons;
    private List<EnrollementResponse> enrollments;
    private TeacherResponse teacher;
    private CategoryResponse category;
}
