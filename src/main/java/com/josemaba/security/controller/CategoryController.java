package com.josemaba.security.controller;

import com.josemaba.security.dto.SaveCategory;
import com.josemaba.security.persistence.entity.Category;
import com.josemaba.security.service.CategoryService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasAuthority('READ_ALL_CATEGORIES')")
    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable){
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if(categoryPage.hasContent()){
            return ResponseEntity.ok(categoryPage);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('READ_ONE_CATEGORY')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findOneById(@PathVariable Long categoryId){
        Optional<Category> category = categoryService.findOneById(categoryId);

        if(category.isPresent()){
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('CREATE_ONE_CATEGORY')")
    @PostMapping
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryService.createOne(saveCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PreAuthorize("hasAuthority('UPDATE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateOneById(@PathVariable Long categoryId,
            @RequestBody @Valid SaveCategory saveCategory){
        Category category = categoryService.updateOneById(categoryId,saveCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PreAuthorize("hasAuthority('DISABLE_ONE_CATEGORY')")
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> diableOneById(@PathVariable Long categoryId){
        Category category = categoryService.disableOneById(categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
}
