package org.backend.modules.enrollment.repository;

import org.backend.domains.learning.Enrollment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {

   boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Enrollment> findByStudentId(Long studentId);
}
