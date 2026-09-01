package org.backend.modules.studentAnswer.repository;

import org.backend.domains.assessment.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAnswerRepository
        extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByStudentId(Long studentId);

    Optional<StudentAnswer> findByStudentIdAndQuestionId(
            Long studentId,
            Long questionId
    );
}