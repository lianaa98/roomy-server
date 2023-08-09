package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.OwnedItem;
import org.springframework.data.repository.CrudRepository;

public interface OwnedItemRepository extends CrudRepository<OwnedItem, Long> {
    OwnedItem[] findByLocationId(Long locationId);
}
