package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.Space;
import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.models.UserSpace;
import com.lianaa98.roomy.repositories.SpaceRepository;
import com.lianaa98.roomy.repositories.UserRepository;
import com.lianaa98.roomy.repositories.UserSpaceRepository;
import com.lianaa98.roomy.requests.CreateSpaceRequest;
import com.lianaa98.roomy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lianaa98.roomy.common.Status.badRequest;
import static com.lianaa98.roomy.common.Status.unauthorized;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UserSpaceController {

    @Autowired
    private UserSpaceRepository userSpaceRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

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

        // space name can be duplicated
        // create space
        Space space = new Space();
        space.name = createSpaceRequest.name;
        spaceRepository.save(space);
        // create user space
        UserSpace userSpace = new UserSpace();
        userSpace.user = user;
        userSpace.space = space;
        userSpaceRepository.save(userSpace);
        return ok(userSpace);
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

        // find user space
        UserSpace userSpace = userSpaceRepository.findByUserIdAndSpaceId(user.id, spaceId);
        System.out.println(userSpace);
        if (userSpace == null) {
            return badRequest();
        }

        // delete user space
        userSpaceRepository.delete(userSpace);
        spaceRepository.delete(userSpace.space);
        return ok(null);
    }
}


