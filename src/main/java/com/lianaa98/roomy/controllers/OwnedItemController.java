package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.OwnedItem;
import com.lianaa98.roomy.models.Space;
import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.LocationRepository;
import com.lianaa98.roomy.repositories.OwnedItemRepository;
import com.lianaa98.roomy.repositories.SpaceRepository;
import com.lianaa98.roomy.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lianaa98.roomy.common.Status.notFound;
import static com.lianaa98.roomy.common.Status.unauthorized;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class OwnedItemController {

    @Autowired
    private OwnedItemRepository ownedItemRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/spaces/{spaceId}/owned_items")
    public ResponseEntity<?> getOwnedItemsBySpaceId(
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
        if(space == null) {
            return notFound();
        }
        // get owned items
        OwnedItem[] ownedItems = ownedItemRepository.findBySpaceId(spaceId);
        return ok(ownedItems);
    }

    @GetMapping("/owned_items/{ownedItemId}")
    public ResponseEntity<?> getOwnedItem(
            @PathVariable Long ownedItemId,
            @RequestHeader("Authorization") String jwt
    ) {
        // validate user
        User user = jwtUtils.getUserFromToken(jwt);
        if (user == null) {
            return unauthorized();
        }

        OwnedItem ownedItem = ownedItemRepository.findById(ownedItemId).orElse(null);
        if (ownedItem == null) {
            return notFound();
        }
        return ok(ownedItem);
    }

}
