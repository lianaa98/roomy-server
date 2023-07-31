package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findByName(String name);
}