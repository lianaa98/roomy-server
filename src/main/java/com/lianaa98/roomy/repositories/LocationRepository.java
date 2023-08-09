package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findBySpaceIdAndName(Long spaceId, String name);

    Location[] findBySpaceId(Long spaceId);
}