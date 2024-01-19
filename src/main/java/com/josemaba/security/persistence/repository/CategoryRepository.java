package com.josemaba.security.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josemaba.security.persistence.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
