package org.backend.modules.teacher.repository;

import org.backend.domains.profile.TeacherProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeacherRepository extends CrudRepository<TeacherProfile, Long> {

    Optional<TeacherProfile> findByUserId(Long userId);
}
