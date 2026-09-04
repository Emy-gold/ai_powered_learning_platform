package org.backend.modules.category.mapper;

import org.backend.domains.learning.Category;
import org.backend.modules.category.dto.CategoryRequest;
import org.backend.modules.category.dto.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .icon(category.getIcon())
                .build();
    }

    public Category toEntity(CategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        return category;
    }
}