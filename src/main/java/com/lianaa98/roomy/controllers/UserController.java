package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.UserRepository;
import com.lianaa98.roomy.requests.LoginRequest;
import com.lianaa98.roomy.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.lianaa98.roomy.common.Status.notFound;
import static com.lianaa98.roomy.common.Status.unauthorized;
import static com.lianaa98.roomy.utils.PasswordUtils.hashPassword;
import static com.lianaa98.roomy.utils.PasswordUtils.matchPassword;
import static org.springframework.http.ResponseEntity.ok;

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
            @RequestBody RegisterRequest registerRequest
    ) {
        User user = new User();
        user.firstName = registerRequest.firstName;
        user.lastName = registerRequest.lastName;
        user.email = registerRequest.email;
        user.username = registerRequest.username;
        user.passwordHash = hashPassword(registerRequest.password);

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    ) {
        User user = userRepository.findByUsername(loginRequest.username);

        if (user == null) {
            return notFound();
        }

        if (matchPassword(loginRequest.password, user.passwordHash)) {
            return ok(user);
        }

        return unauthorized();
    }

}
