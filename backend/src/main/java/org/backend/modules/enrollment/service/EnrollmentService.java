package org.backend.modules.enrollment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Course;
import org.backend.domains.learning.Enrollment;
import org.backend.domains.profile.StudentProfile;
import org.backend.modules.course.repository.CourseRepository;
import org.backend.modules.enrollment.dto.EnrollmentRequest;
import org.backend.modules.enrollment.dto.EnrollmentResponse;
import org.backend.modules.enrollment.mapper.EnrollmentMapper;
import org.backend.modules.enrollment.repository.EnrollmentRepository;
import org.backend.modules.student.repository.StudentRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentProfileRepository;
    private final EnrollmentMapper enrollmentMapper;

    //-------------------------------Enroll in a course---------------------------------
    @Transactional
    public EnrollmentResponse enroll(EnrollmentRequest request, Long userId){

        StudentProfile student = studentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())) {
            throw new RuntimeException("Already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrollmentDate(LocalDateTime.now())
                // .status(EnrollmentStatus.ACTIVE) // set once you confirm the enum values
                .build();

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    //-------------------------------Get enrollment by id, scoped to owner---------------
    public EnrollmentResponse getById(Long id, Long userId){
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (!enrollment.getStudent().getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this enrollment");
        }

        return enrollmentMapper.toResponse(enrollment);
    }

    //-------------------------------Get all enrollments for the current student---------
    public List<EnrollmentResponse> getByStudent(Long userId){
        StudentProfile student = studentProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        return enrollmentRepository.findByStudentId(student.getId())
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }

    //-------------------------------Unenroll from a course-------------------------------
    @Transactional
    public void unenroll(Long enrollmentId, Long userId){
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (!enrollment.getStudent().getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You do not own this enrollment");
        }

        enrollmentRepository.delete(enrollment);
    }
}