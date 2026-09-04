package org.backend.modules.category.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.domains.learning.Category;
import org.backend.modules.category.dto.CategoryRequest;
import org.backend.modules.category.dto.CategoryResponse;
import org.backend.modules.category.mapper.CategoryMapper;
import org.backend.modules.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    //-------------------------------Create the category---------------------------------
    @Transactional
    public CategoryResponse create(CategoryRequest request){
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    //-------------------------------Get category by id------------------------------------
    public CategoryResponse getById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toResponse(category);
    }

    //-------------------------------Get all categories------------------------------------
    public List<CategoryResponse> getAll(){
        List<CategoryResponse> categories = new ArrayList<>();
        for (Category category : categoryRepository.findAll()){
            categories.add(categoryMapper.toResponse(category));
        }
        return categories;
    }

    //-------------------------------Update the category------------------------------------
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    //-------------------------------Delete the category-------------------------------------
    @Transactional
    public void delete(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }
}