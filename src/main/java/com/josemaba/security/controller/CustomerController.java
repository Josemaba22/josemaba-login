package com.josemaba.security.controller;

import org.springframework.web.bind.annotation.RestController;

import com.josemaba.security.dto.RegisteredUser;
import com.josemaba.security.dto.SaveUser;
import com.josemaba.security.persistence.entity.User;
import com.josemaba.security.service.auth.AuthenticationService;

import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @PreAuthorize("permitAll")
    @PostMapping("/register")
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser newUser){
        RegisteredUser registeredUser = authenticationService.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }   

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(@RequestParam String name){
        return ResponseEntity.ok("Hello " + name);
    }

    @PreAuthorize("denyAll")
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(Arrays.asList());
    }
    
}
