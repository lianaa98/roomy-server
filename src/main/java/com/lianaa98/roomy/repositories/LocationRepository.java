package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findByName(String name);

    Location findBySpaceIdAndName(Long spaceId, String name);

//    @Query(
//            value = "SELECT * FROM locations l " +
//                    "JOIN spaces s ON l.space_id = s.id " +
//                    "JOIN users_spaces us ON s.id = us.space_id " +
//                    "JOIN users u ON us.user_id = u.id " +
//                    "WHERE s.id = ?1 AND u.id = ?2"
//    )
//    Location findBySpaceIdAndUserId(Long spaceId, Long userId);
}