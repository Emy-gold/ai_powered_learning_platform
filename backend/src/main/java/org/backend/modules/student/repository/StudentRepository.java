package org.backend.modules.student.repository;

import org.backend.domains.profile.StudentProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<StudentProfile, Long> {

    Optional<StudentProfile> findByUserId(Long userId);
}