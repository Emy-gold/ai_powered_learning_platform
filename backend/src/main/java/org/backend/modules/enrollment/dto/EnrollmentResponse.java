package org.backend.modules.enrollment.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.backend.domains.learning.EnrollmentStatus;
import org.backend.modules.course.dto.CourseResponse;
import org.backend.modules.student.dto.StudentResponse;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class EnrollmentResponse {

    private Long id;
    private LocalDateTime enrollmentDate;
    private EnrollmentStatus status;
    private StudentResponse student;
    private CourseResponse course;

}
