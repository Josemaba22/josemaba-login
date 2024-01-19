package com.josemaba.security.service;

import com.josemaba.security.dto.SaveCategory;
import com.josemaba.security.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(SaveCategory saveCategory);

    Category updateOneById(Long categoryId,SaveCategory saveCategory);

    Category disableOneById(Long categoryId);
}
