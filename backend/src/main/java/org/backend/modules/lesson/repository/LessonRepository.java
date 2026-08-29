package org.backend.modules.lesson.repository;

import org.backend.domains.learning.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends CrudRepository<Lesson, Long> {

}
