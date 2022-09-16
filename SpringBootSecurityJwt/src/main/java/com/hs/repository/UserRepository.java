package com.hs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hs.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String username);
}
