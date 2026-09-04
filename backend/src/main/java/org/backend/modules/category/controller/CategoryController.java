package org.backend.modules.category.controller;

import lombok.RequiredArgsConstructor;
import org.backend.modules.category.dto.CategoryRequest;
import org.backend.modules.category.dto.CategoryResponse;
import org.backend.modules.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService service;

    //------------------------------Post endpoint for creating categories---------------------------
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    //------------------------------Get the category by id------------------------------------------
    @GetMapping("{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //-----------------------------Get all categories-------------------------------------------------
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    //---------------------------Update the category--------------------------------------------------
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable Long id,
            @RequestBody CategoryRequest request
    ){
        return ResponseEntity.ok(service.update(id, request));
    }

    //---------------------------Delete the category---------------------------------------------------
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}