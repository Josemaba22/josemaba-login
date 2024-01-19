package com.josemaba.security.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josemaba.security.persistence.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
