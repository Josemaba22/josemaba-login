package com.josemaba.security.service;

import java.util.Optional;

import com.josemaba.security.dto.SaveUser;
import com.josemaba.security.persistence.entity.User;

public interface UserService {

    User registerOneCustomer(SaveUser newUser);

    Optional<User> findOneByUsername(String username);

}
