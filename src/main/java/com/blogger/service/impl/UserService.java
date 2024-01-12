package com.blogger.service.impl;

import com.blogger.entity.Role;
import com.blogger.entity.User;
import com.blogger.exception.BlogAPIException;
import com.blogger.exception.DataAlreadyExists;
import com.blogger.payload.SignUpDto;
import com.blogger.repository.RoleRepository;
import com.blogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
            private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String signup(SignUpDto signUpDto){

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw  new DataAlreadyExists("Username is Already Registered");
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw  new DataAlreadyExists("Email is Already Registered");

        }
        try{
            LocalDateTime signupdatetime  =LocalDateTime.now();
            User user = new User();
            String userid = UUID.randomUUID().toString();
            user.setId(userid);
            user.setName(signUpDto.getName());
            user.setUsername(signUpDto.getUsername());
            user.setEmail(signUpDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            user.setSignupdatetime(signupdatetime.toString());
            Role roles = roleRepository.findByName("ROLE_USER").get();
            user.setRoles(Collections.singleton(roles));
            userRepository.save(user);
        }
        catch (Exception e){
            throw new BlogAPIException("Something went wroong,Registration failed");
        }
      return "User Registered Successfully";
    }

    public List<User> getalluser() {
        List<User> all = userRepository.findAll();
        List<User> sorted =all.stream().sorted(Comparator.comparing(User::getSignupdatetime).reversed()).collect(Collectors.toList());
        return sorted;
    }
}

