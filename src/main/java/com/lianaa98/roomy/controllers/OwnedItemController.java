package com.lianaa98.roomy.controllers;

import com.lianaa98.roomy.models.OwnedItem;
import com.lianaa98.roomy.models.Space;
import com.lianaa98.roomy.models.User;
import com.lianaa98.roomy.repositories.LocationRepository;
import com.lianaa98.roomy.repositories.OwnedItemRepository;
import com.lianaa98.roomy.repositories.SpaceRepository;
import com.lianaa98.roomy.repositories.UserRepository;
import com.lianaa98.roomy.requests.EditOwnedItemRequest;
import com.lianaa98.roomy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    private UserRepository userRepository;

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

    @PostMapping("/owned_items/{ownedItemId}")
    public ResponseEntity<?> editOwnedItem(
            @PathVariable Long ownedItemId,
            @RequestHeader("Authorization") String jwt,
            @RequestBody EditOwnedItemRequest editOwnedItemRequest
    ) {
        // validate user
        User user = jwtUtils.getUserFromToken(jwt);
        if (user == null) {
            return unauthorized();
        }

        Space itemBelongsToSpace = ownedItemRepository.findSpaceByOwnedItemId(ownedItemId).get(0);
        if (itemBelongsToSpace == null) {
            return notFound();
        }
        System.out.println("itemBelongsToSpace: " + itemBelongsToSpace);
        if (!itemBelongsToSpace.users.contains(user)) {
            return unauthorized();
        }

        OwnedItem ownedItem = ownedItemRepository.findById(ownedItemId).orElse(null);
        if (ownedItem == null) {
            return notFound();
        }

        if (editOwnedItemRequest.name.length() > 0) {
            ownedItem.name = editOwnedItemRequest.name;
        }
        if (editOwnedItemRequest.brand.length() > 0) {
            ownedItem.brand = editOwnedItemRequest.brand;
        }
        if (editOwnedItemRequest.description.length() > 0) {
            ownedItem.description = editOwnedItemRequest.description;
        }
        if (editOwnedItemRequest.quantity != null) {
            ownedItem.quantity = editOwnedItemRequest.quantity;
        }
        if (editOwnedItemRequest.status.length() > 0) {
            ownedItem.status = editOwnedItemRequest.status;
        }
        if (editOwnedItemRequest.locationId != null) {
            ownedItem.location = locationRepository.findById(editOwnedItemRequest.locationId).orElse(null);
        }
        if (editOwnedItemRequest.ownerId != null) {
            ownedItem.user = userRepository.findById(editOwnedItemRequest.ownerId).orElse(null);
        }
        ownedItem.updatedAt = new Date();
        ownedItemRepository.save(ownedItem);
        return ok(ownedItem);
    }
}
