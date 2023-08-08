package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.Space;
import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.SpaceRepository;
import com.lianaa98.roomy.repositories.UserRepository;
import com.lianaa98.roomy.requests.CreateSpaceRequest;
import com.lianaa98.roomy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.lianaa98.roomy.common.Status.notFound;
import static com.lianaa98.roomy.common.Status.unauthorized;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserSpaceController {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/spaces")
    public ResponseEntity<?> getSpaces(
            @RequestHeader("Authorization") String jwt
    ) {
        // validate user
        User user = jwtUtils.getUserFromToken(jwt);
        if (user == null) {
            return unauthorized();
        }

        // get user spaces
        return ok(user.spaces);

    }

    @PostMapping("/spaces")
    public ResponseEntity<?> createSpace(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateSpaceRequest createSpaceRequest
    ) {
        // validate user
        User user = jwtUtils.getUserFromToken(jwt);
        if (user == null) {
            return unauthorized();
        }

        // create space
        Space space = new Space();
        space.name = createSpaceRequest.name;
        space.createdAt = new java.util.Date();
        space.updatedAt = new java.util.Date();
        spaceRepository.save(space);
        // create user space
        user.spaces.add(space);
        userRepository.save(user);

        return ok(space);
    }

    @DeleteMapping("/spaces/{spaceId}")
    public ResponseEntity<?> deleteSpace(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long spaceId
    ) {
        // validate user
        User user = jwtUtils.getUserFromToken(jwt);
        if (user == null) {
            return unauthorized();
        }

        // validate space
        Space space = spaceRepository.findById(spaceId).orElse(null);
        if (space == null) {
            return notFound();
        }
        // check if user exist in space.users
        User userCheck = space.users.stream()
                .filter(user1 ->
                        user1.id.equals(user.id))
                .findFirst()
                .orElse(null);

        if (userCheck == null) {
            return unauthorized();
        }

        // delete space
        user.spaces.remove(space);
        spaceRepository.delete(space);
        return ok(null);
    }
}


