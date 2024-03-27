package com.josemaba.security.persistence.repository.security;

import com.josemaba.security.persistence.entity.security.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
    Optional<Role> findByName(String defaultRole);

}
