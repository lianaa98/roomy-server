package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.Location;
import com.lianaa98.roomy.models.Space;
import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.LocationRepository;
import com.lianaa98.roomy.repositories.SpaceRepository;
import com.lianaa98.roomy.requests.CreateSpaceRequest;
import com.lianaa98.roomy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.lianaa98.roomy.common.Status.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/spaces/{spaceId}/locations")
    public ResponseEntity<?> addLocationToSpace(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long spaceId,
            @RequestBody CreateSpaceRequest locationRequest
    ) {
        // validate user
        User user = jwtUtils.getUserFromToken(jwt);
        if (user == null) {
            return unauthorized();
        }
        // validate space
        Space space = spaceRepository.findById(spaceId).orElse(null);
        if(space == null) {
            return notFound();
        }
        Location location = locationRepository.findBySpaceIdAndName(spaceId, locationRequest.name);
        if (location != null) {
            return conflict("Location name already exists");
        }
        // create location
        Location newLocation = new Location();
        newLocation.name = locationRequest.name;
        newLocation.space = space;
        locationRepository.save(newLocation);

        return ok(newLocation);
    }

    @GetMapping("/spaces/{spaceId}/locations")
    public ResponseEntity<?> getAllLocationsInUserSpace(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long spaceId
    ) {
        User user = jwtUtils.getUserFromToken(jwt);
        if (user == null) {
            return unauthorized();
        }

        List<Space> spaces = user.spaces;

        Space space = spaces.stream()
                            .filter(space1 -> space1.id == spaceId)
                            .findFirst()
                            .orElse(null);

        if (space == null) {
            return notFound();
        }

        return ok(locationRepository.findBySpaceId(spaceId));
    }
}
