package com.josemaba.security.controller;

import org.springframework.web.bind.annotation.RestController;

import com.josemaba.security.dto.RegisteredUser;
import com.josemaba.security.dto.SaveUser;
import com.josemaba.security.service.auth.AuthenticationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @PostMapping
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser newUser){
        RegisteredUser registeredUser = authenticationService.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }   
}
