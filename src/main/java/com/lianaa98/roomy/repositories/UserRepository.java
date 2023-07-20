package com.lianaa98.roomy.repositories;

import com.lianaa98.roomy.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
