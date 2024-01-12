package com.hs.repository;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findByFirstName(String firstName);
}
