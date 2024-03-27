package com.josemaba.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.josemaba.security.persistence.entity.security.Role;
import com.josemaba.security.persistence.repository.security.RoleRepository;
import com.josemaba.security.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Value("${security.default.roles}")
    private String defaultRole;

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
    

}
