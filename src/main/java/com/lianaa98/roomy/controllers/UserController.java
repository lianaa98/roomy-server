package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.UserRepository;
import com.lianaa98.roomy.requests.LoginRequest;
import com.lianaa98.roomy.requests.RegisterRequest;
import com.lianaa98.roomy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lianaa98.roomy.common.Status.*;
import static com.lianaa98.roomy.utils.PasswordUtils.hashPassword;
import static com.lianaa98.roomy.utils.PasswordUtils.matchPassword;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/users/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @RequestBody RegisterRequest registerRequest
    ) {

        if (jwt != null) {
            return forbidden("already logged in");
        }

        // if email or username already exists, throw conflict
        if (userRepository.findByEmail(registerRequest.email) != null
                || userRepository.findByUsername(registerRequest.username) != null) {
            return conflict("Email or username already exists");
        }

        User user = new User();
        user.firstName = registerRequest.firstName;
        user.lastName = registerRequest.lastName;
        user.email = registerRequest.email;
        user.username = registerRequest.username;
        user.passwordHash = hashPassword(registerRequest.password);

        userRepository.save(user);

        String token = jwtUtils.generateToken(user.username);

        return ok().body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestHeader(value = "Authorization", required = false) String jwt,
            @RequestBody LoginRequest loginRequest
    ) {

        if (jwt != null) {
            return forbidden("already logged in");
        }

        User user = userRepository.findByUsername(loginRequest.username);

        if (user == null) {
            return notFound();
        }

        if (matchPassword(loginRequest.password, user.passwordHash)) {
            String token = jwtUtils.generateToken(user.username);
            return ok().body(token);
        }

        return unauthorized();
    }

    @GetMapping("/verifyUser")
    public ResponseEntity<?> verifyToken(
            @RequestHeader(value = "Authorization") String jwt
    ) {
        if (jwtUtils.getUserFromToken(jwt) == null) {
            return unauthorized();
        }
        return ok().body(null);
    }

}
