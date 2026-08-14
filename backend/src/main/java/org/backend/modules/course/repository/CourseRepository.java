package org.backend.modules.course.repository;

import org.backend.domains.learning.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Optional<Course> findByUserId(Long userId);
    boolean exitsByUserId(Long userId);
}
