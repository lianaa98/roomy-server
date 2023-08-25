package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.OwnedItem;
import com.lianaa98.roomy.models.Space;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnedItemRepository extends CrudRepository<OwnedItem, Long> {
    OwnedItem[] findByLocationId(Long locationId);

    @Query(
            value = "SELECT * FROM owned_items WHERE location_id IN (SELECT id FROM locations WHERE space_id = ?1)",
            nativeQuery = true
    )
    OwnedItem[] findBySpaceId(Long spaceId);

    @Query(
            value = "SELECT s " +
                    "FROM OwnedItem oi " +
                    "JOIN oi.location l " +
                    "JOIN l.space s " +
                    "WHERE oi.id = ?1")
    List<Space> findSpaceByOwnedItemId(Long ownedItemId);
}
