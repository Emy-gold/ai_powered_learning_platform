package org.backend.modules.enrollment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Course;
import org.backend.domains.learning.Enrollment;
import org.backend.domains.learning.EnrollmentStatus;
import org.backend.domains.profile.StudentProfile;
import org.backend.modules.course.repository.CourseRepository;
import org.backend.modules.enrollment.dto.EnrollmentRequest;
import org.backend.modules.enrollment.dto.EnrollmentResponse;
import org.backend.modules.enrollment.mapper.EnrollmentMapper;
import org.backend.modules.enrollment.repository.EnrollmentRepository;
import org.backend.modules.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    public EnrollmentResponse enroll(EnrollmentRequest request, Long userId){

        StudentProfile student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if( enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())){
            throw new RuntimeException("Already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollmentDate(LocalDateTime.now())
                .status(EnrollmentStatus.ACTIVE)
                .build();

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }
}
