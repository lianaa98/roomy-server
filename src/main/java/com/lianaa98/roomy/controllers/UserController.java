package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.UserRepository;
import com.lianaa98.roomy.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.lianaa98.roomy.utils.PasswordUtils.hashPassword;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public User register(
            @RequestBody UserRequest userRequest
    ) {
        User user = new User();
        user.firstName = userRequest.firstName;
        user.lastName = userRequest.lastName;
        user.email = userRequest.email;
        user.username = userRequest.username;
        user.passwordHash = hashPassword(userRequest.password);

        return userRepository.save(user);
    }

}
