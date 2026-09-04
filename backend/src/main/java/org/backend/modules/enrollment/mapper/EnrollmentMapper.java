package org.backend.modules.enrollment.mapper;

import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Enrollment;
import org.backend.modules.course.mapper.CourseMapper;
import org.backend.modules.enrollment.dto.EnrollmentRequest;
import org.backend.modules.enrollment.dto.EnrollmentResponse;
import org.backend.modules.student.mapper.StudentMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EnrollmentMapper {

    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    public EnrollmentResponse toResponse(Enrollment enrollment){

        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .status(enrollment.getStatus())
                .student(studentMapper.toResponse(enrollment.getStudent()))
                .course(courseMapper.toResponse(enrollment.getCourse()))
                .build();
    }
}
