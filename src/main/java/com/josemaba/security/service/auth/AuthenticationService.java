package com.josemaba.security.service.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.josemaba.security.dto.AuthenticationRequest;
import com.josemaba.security.dto.AuthenticationResponse;
import com.josemaba.security.dto.RegisteredUser;
import com.josemaba.security.dto.SaveUser;
import com.josemaba.security.exception.ObjectNotFoundException;
import com.josemaba.security.persistence.entity.User;
import com.josemaba.security.service.UserService;

import jakarta.validation.Valid;


@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {

        User user = userService.registerOneCustomer(newUser);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        userDto.setJwt(jwt);
        
        return userDto;

    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities",  user.getAuthorities());

        return extraClaims;
        
    }

    public AuthenticationResponse login(@Valid AuthenticationRequest authRequest) {
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(authentication);

        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt(jwt);

        return response;
        
    }

    public boolean validateToken(String jwt) {

        try{
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    public User findLoggerInUser() {

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) auth.getPrincipal();
        
        return userService.findOneByUsername(username)
            .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));

    }
    
}
