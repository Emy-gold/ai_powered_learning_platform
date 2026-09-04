package org.backend.modules.enrollment.repository;

import org.backend.domains.learning.Enrollment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {

   boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
