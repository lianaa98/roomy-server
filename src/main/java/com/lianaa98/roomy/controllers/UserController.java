package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

}
