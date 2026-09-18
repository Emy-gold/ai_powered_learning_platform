package org.backend.modules.progress.repository;

import org.backend.domains.learning.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByStudentId(Long studentId);

    List<Progress> findByStudentIdAndCourseId(Long studentId, Long courseId);

    Optional<Progress> findByStudentIdAndLessonId(Long studentId, Long lessonId);
}