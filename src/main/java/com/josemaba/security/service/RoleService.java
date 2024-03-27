package com.josemaba.security.service;

import java.util.Optional;

import com.josemaba.security.persistence.entity.security.Role;

public interface RoleService {
    
    Optional<Role> findDefaultRole();
}
