package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.OwnedItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OwnedItemRepository extends CrudRepository<OwnedItem, Long> {
    OwnedItem[] findByLocationId(Long locationId);

    @Query(
        value = "SELECT * FROM owned_items WHERE location_id IN (SELECT id FROM locations WHERE space_id = ?1)",
        nativeQuery = true
    )
    OwnedItem[] findBySpaceId(Long spaceId);
}
