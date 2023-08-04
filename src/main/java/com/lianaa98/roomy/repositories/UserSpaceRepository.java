package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.UserSpace;
import org.springframework.data.repository.CrudRepository;

public interface UserSpaceRepository extends CrudRepository<UserSpace, Long> {

    UserSpace[] findByUserId(Long userId);
    UserSpace findByUserIdAndSpaceId(Long userId, Long spaceId);
}
