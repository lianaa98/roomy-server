package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.UserSpace;

public interface UserSpaceRepository extends CrudRepository<UserSpace, Long>{
    UserSpace findByUserIdAndSpaceId(Long userId, Long spaceId);
}
