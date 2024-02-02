package com.josemaba.security.service;

import com.josemaba.security.dto.SaveUser;
import com.josemaba.security.persistence.entity.User;

public interface UserService {

    User registerOneCustomer(SaveUser newUser);

}
