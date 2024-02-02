package com.josemaba.security.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josemaba.security.dto.RegisteredUser;
import com.josemaba.security.dto.SaveUser;
import com.josemaba.security.persistence.entity.User;
import com.josemaba.security.service.UserService;


@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {
        User user = userService.registerOneCustomer(newUser);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user);
        userDto.setJwt(jwt);
        
        return userDto;
    }
    
}
