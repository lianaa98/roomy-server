package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.Space;
import org.springframework.data.repository.CrudRepository;

public interface SpaceRepository extends CrudRepository<Space, Long> {
    Space findByName(String name);
}
