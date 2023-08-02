package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.Location;
import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.LocationRepository;
import com.lianaa98.roomy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.lianaa98.roomy.common.Status.unauthorized;

@RestController
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private JwtUtils jwtUtils;

//    @GetMapping("/space/{spaceId}/locations")
//    public ResponseEntity<?> getAllLocationsInUserSpace(
//            @RequestHeader("Authorization") String jwt,
//            @PathVariable Long spaceId
//    ) {
//        // validate user
//        User user = jwtUtils.getUserFromToken(jwt);
//        if (user == null) {
//            return unauthorized();
//        }
//        return ResponseEntity.ok(locationRepository.findBySpaceIdAndUserId(spaceId, user.id));
//    }

}
