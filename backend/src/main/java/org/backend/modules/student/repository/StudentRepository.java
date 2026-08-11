package org.backend.modules.student.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<StudentRepository, Long> {

    @Override
    Optional<StudentRepository> findById(Long aLong);
}
