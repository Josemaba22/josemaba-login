package com.josemaba.security.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.josemaba.security.persistence.entity.security.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    
}
