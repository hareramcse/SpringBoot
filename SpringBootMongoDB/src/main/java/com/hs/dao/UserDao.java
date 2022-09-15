package com.hs.dao;

import java.util.List;

import com.hs.model.User;

public interface UserDao {

	List<User> getAllUsers();

	User getUserById(String userId);

	User addNewUser(User user);
}
