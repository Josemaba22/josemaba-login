package com.josemaba.security.controller;

import com.josemaba.security.dto.SaveProduct;
import com.josemaba.security.persistence.entity.Product;
import com.josemaba.security.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable){
        Page<Product> productsPage = productService.findAll(pageable);
        if(productsPage.hasContent()){
            return ResponseEntity.ok(productsPage);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId){
        Optional<Product> product = productService.findOneById(productId);

        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProduct saveProduct){
        Product product = productService.createOne(saveProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'ASSISTANT_ADMINISTRATOR')")
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById(@PathVariable Long productId,
            @RequestBody @Valid SaveProduct saveProduct){
        Product product = productService.updateOneById(productId ,saveProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> diableOneById(@PathVariable Long productId){
        Product product = productService.disableOneById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
