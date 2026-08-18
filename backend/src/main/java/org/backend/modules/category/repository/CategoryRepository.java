package org.backend.modules.category.repository;

import org.backend.domains.learning.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
